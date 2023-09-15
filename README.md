# bank-system
Тестовое задание в компанию "СМАРТС"

# Результаты правок
был переписан слой валидации, для соответствия SOLID принципам - теперь для каждый сущности существует отдельный валидатор. Логика обработки паспорта и телефонного номера клиента для отправки в бд, была также вынесена в отдельный класс. 

Добавилась новая ошибка - ServerException, которая выбрасывается во время работы операций с бд

В условиях ограничения по времени, для реализации UI была использована библиотека Thymeleaf , работа с которой упрощает общение между клиентом и сервером.
Текущую реализацию можно значительно улучшить путем перехода на RestTemplate с  использованием кодов ошибок и http статусов. В качестве объекта обмена данными в таком случае выступал бы json, который парсился на стороне клиента.
Хорошим решением обработки ошибок (без использования Thymeleaf) было созданием в domain сущностях списка классов Error. Класс Error хранил бы в себе информацию о  сущности, в которой произошла валидационная ошибка, а так же поле, описание ошибки, кода ошибки с использованием регулярных выражений, представленный в виде enum (Пример: CODE_001("%s-001", "Entity field error", "Field '%s' should not be empty")). В валидаторе, при наличии ошибок, объект Error добавлялся в список errors у сущности, а после прохождения валидации выполнялась проверка на размер списка ошибок, при наличии которых возвращалась страница, уведомляющая о произошедших ошибках.
Обработка ошибок слоя DAO, при ситуации описанной выше, представляла из себя проброс ошибок до контроллеров, в которых происходил их отлов, и в зависимости от типа ошибки, клиенту возвращался тот или иной http-статус.

# Описание
Программа содержит пять сущностей:
*  Client - содержит поля: именем, фамилией, номером телефона, email и номер паспорта
*  Credit - содержет поля: лимит по кредиту, процентная ставка (данные значения не могут выходить за границы, описанные в конфигурации application.yaml, bank.credit.max-limit(максимально допустимый кредит, который может выдать банк), min-interest-range (минимальная процентная ставка, под которую банк может выдать кредит). Входящие значения, проходят проверку в Validation)
*  CrediOffer - содержит поля: Client (Один к одному), Credit (Один к одному), длительность (На сколько выдается кредит), список содержащий платежи по месяцам
*  PaymentEvent - содержит поля: CrediOffer, дата в которую нужно провести оплату, сумма платежа, сумма гашения тела кредита, сумма гашения процентов.
*  Bank - содержит поля: название, список кредитов (Один ко многим), список клиентов(Один ко многим).

Сущность CrediOffer не подразумевает возможность изменения, для соблюдения безопасности - ее можно только удалить и пересоздать заново.

Список PaymentEvent у CreditOffer наполняется автоматически, при формировании предложения, и хранится в базе до момента пока не будет удалена сущность CreditOffer.

# Проверка проперти
Проверка проперти для сущности СreditOfferValidator выполняет после инициализации бина в классе BeanPostProcessorImpl. Данная реализация необходима для того, что-бы до запуска приложения, проверить соответсвие параметров, и в случае несоответствия - прервать запуск.

# Валидация
Валидация осуществляется при помощи отдельного слоя. Для каждой сущности существует отдельная реализация интерфейса Validator<T>. 

Во время валидации, так-же проводится форматирование полей под единый стиль. В текущей реализаии это поля клиента: номер телефона и номер паспорта. Форматирование осуществляется в классе ClientFormatter

Во время валидации сущности, происходит проверка каждого поля и в случае возникновения ошибки, в строку errorMessage добавляется соответствующее сообщение с разделителем - '|', а так-же переменна noError становится false.   
в конце метода, происходит проверка - если переменная noError - false - пробрасывается кастомная ValidationException, в которую помещается собранное сообщение об ошибках, иначе валидационный метод возвращает true и программа продолжает свою работу

Проверка подсущностей происходит при проверке соответствующего поля у основной сущности, которая выбросит ошибку, в случае если подсущность не прошла валидацию. Дальнейшая проверка будет прервана.    

Валидция PaymentEvent не проводится, т.к. он создается программно, необходимости в валидации нет.

# Отлов ошибок
За отлов ошибок отвечает класс GlobalExceptionHandler, который отлавливает кастомные ошибки и редеректит на страницу ошибки, передавая сообщение в случае проброса кастомной ошибки, и сообщение "server error" в случае любых других ошибок 

# Формирование графика платежей
При создании(сохранении) кредитного предложения (CrediOffer), при помощи метода CreditOfferServiceImpl.buildPaymentGraph(CreditOffer creditOffer) - выполняются необходимые расчеты и формируется график платежей, который представлен в виде списка PaymentEvents. В дальнейшем все созданные ивенты сохраняются пачкой в базу данных, а уже после выводятся пользователю. В конфигурации присутствует пункт bank.credit-offer.scale - он отвечает за округление подсчета платежей и процентов

Возможности удалить или изменить ивент - нет. Т.к. построение кредитного предложения выполнено на основе аннуитетного платежа, который подразумевает что выплачиваемая сумма остается неизменной на протяжении оплаты всего кредита.

# Профилирование
В разработанном приложении представлены три профиля, каждый из которых имеет отдельную базу данных. Они располагаются в /db в корневой папке проекта: 
*  dev - профиль для разработки, включает в себя автоматическое наполнение бд и пересоздание бд при старте приложения
*  prod - профиль для продакшена
*  test - профиль для тестирования

# База данных
Скрипт для создания бд находится в папке /resources/db/V1__init.sql

Скрипт для наполнения бд для тестов находится в папке /resources/data.sql

Базы данных для всех профилей хранятся в папке /db

