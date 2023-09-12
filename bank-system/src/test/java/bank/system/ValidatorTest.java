package bank.system;

import bank.system.model.domain.Client;
import bank.system.model.domain.Credit;
import bank.system.model.domain.CreditOffer;
import bank.system.rest.Validator;
import bank.system.rest.exception.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = "test")
public class ValidatorTest {

    @Autowired
    private Validator validator;

    @Value("${bank.credit.max-limit}")
    double maxCreditLimit;

    @Value("${bank.credit.min-interest-rate}")
    double minInterestRate;
    @Test
    public void clientValidationPositiveTest() {
        Client client = Client.builder()
                .email("KorkWake@smarts.ru")
                .firstName("Kork")
                .passportID("91-77-856785")
                .phoneNumber("8-958-765-67-78")
                .secondName("Wake")
                .build();
        assertEquals(validator.clientValidation(client),client);
    }

    @Test
    public void clientValidationNegativeTest1() {
        Client client = Client.builder()
                .email("KorkWake.smarts.@u")
                .firstName("Kork78")
                .passportID("912-727-856785")
                .secondName("Wake56")
                .build();

        ValidationException thrown = assertThrows(ValidationException.class,
                () -> validator.clientValidation(client));
        assertEquals(thrown.getMessage(), "email should be correct value|first name cannot contain numbers|phone number filed must be not be empty|second name cannot contain numbers|passport id must match the pattern: **-**-******|");

    }

    @Test
    public void clientValidationNegativeTest2() {
        Client client = Client.builder()
                .email("KorkWake@smarts.ru")
                .firstName("Kork")
                .passportID("912-717-856785")
                .phoneNumber("8-958-765-67-78")
                .secondName("Wake")
                .build();

        ValidationException thrown = assertThrows(ValidationException.class,
                () -> validator.clientValidation(client));
        assertEquals(thrown.getMessage(), "passport id must match the pattern: **-**-******|");
    }

    @Test
    public void creditValidationPositiveTest() {
        Credit credit = Credit.builder()
                .interestRate(15)
                .limit(20000)
                .build();
        assertEquals(validator.creditValidation(credit),credit);
    }

    @Test
    public void creditValidationNegativeTest1() {
        Credit credit = Credit.builder()
                .interestRate(0)
                .limit(0)
                .build();
        ValidationException thrown = assertThrows(ValidationException.class,
                () -> validator.creditValidation(credit));
        assertEquals(thrown.getMessage(), "credit limit must be positive|interest rate must be positive|");
    }

    @Test
    public void creditValidationNegativeTest2() {

        Credit credit = Credit.builder()
                .interestRate(2)
                .limit(999999999)
                .build();

        ValidationException thrown = assertThrows(ValidationException.class,
                () -> validator.creditValidation(credit));
        assertEquals(thrown.getMessage(), "credit limit must be less "+maxCreditLimit+"|interest rate must be over "+minInterestRate+"|");
    }


    @Test
    public void creditOfferValidationPositiveTest(){
        Credit credit = Credit.builder()
                .interestRate(15)
                .limit(20000)
                .build();
        Client client = Client.builder()
                .email("KorkWake@smarts.ru")
                .firstName("Kork")
                .passportID("91-77-856785")
                .phoneNumber("8-958-765-67-78")
                .secondName("Wake")
                .build();


        CreditOffer creditOffer = CreditOffer.builder()
                .credit(credit)
                .client(client)
                .paymentSum(15000)
                .duration(12)
                .build();


        assertEquals(validator.creditOfferValidation(creditOffer),creditOffer);
    }

    @Test
    public void creditOfferValidationNegativeTest1(){
        Credit credit = Credit.builder()
                .interestRate(15)
                .limit(20000)
                .build();
        Client client = Client.builder()
                .email("KorkWake@smarts.ru")
                .firstName("Kork")
                .passportID("91-77-856785")
                .phoneNumber("8-958-765-67-78")
                .secondName("Wake")
                .build();


        CreditOffer creditOffer = CreditOffer.builder()
                .credit(credit)
                .client(client)
                .paymentSum(999999999)
                .duration(-2)
                .build();

        ValidationException thrown = assertThrows(ValidationException.class,
                () -> validator.creditOfferValidation(creditOffer));
        System.out.println(thrown.getMessage());
        assertEquals(thrown.getMessage(), "credit offer duration must be positive|payment sum must be less than credit limit|");
    }


}
