package bank.system.rest.dao.service.impl;

import bank.system.model.domain.CreditOffer;
import bank.system.model.domain.PaymentEvent;
import bank.system.rest.dao.repository.CreditOfferRepository;
import bank.system.rest.dao.service.api.StorageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CreditOfferServiceImpl implements StorageDAO<CreditOffer, UUID> {


    private final CreditOfferRepository creditOfferRepository;
    private final PaymentEventServiceImpl paymentEventService;


    public CreditOfferServiceImpl(CreditOfferRepository creditOfferRepository, PaymentEventServiceImpl paymentEventService) {
        this.creditOfferRepository = creditOfferRepository;
        this.paymentEventService = paymentEventService;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public CreditOffer save(CreditOffer creditOffer) {
        List<PaymentEvent> paymentGraph = buildPaymentGraph(creditOffer);

        List<PaymentEvent> savedGraph = paymentEventService.bulkSave(paymentGraph);
        creditOffer.setPaymentEventList(savedGraph);

        CreditOffer saved = creditOfferRepository.save(creditOffer);

        return saved;
    }

    @Override
    public List<CreditOffer> getAll() {
        return creditOfferRepository.findAll();
    }

    @Override
    public CreditOffer findById(UUID uuid) {
        return creditOfferRepository.findById(uuid).orElse(null);
    }

    @Override
    public CreditOffer update(CreditOffer creditOffer) {
        CreditOffer savedOffer = creditOfferRepository.findById(creditOffer.getId()).orElse(null);

        if (savedOffer == null) {
            throw new RuntimeException("CreditOffer not found");
        }

        return creditOfferRepository.save(creditOffer);
    }

    @Override
    public boolean remove(CreditOffer creditOffer) {
        creditOfferRepository.delete(creditOffer);
        return true;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean removeById(UUID uuid) {
        CreditOffer creditOffer = findById(uuid);

        for (PaymentEvent p: creditOffer.getPaymentEventList()){
            paymentEventService.removeById(p.getId());
        }

        creditOfferRepository.deleteById(uuid);
        return true;
    }

    private List<PaymentEvent> buildPaymentGraph(CreditOffer creditOffer) {
        List<PaymentEvent> paymentEvents = new ArrayList<>();
        int duration = creditOffer.getDuration();
        double sum = creditOffer.getPaymentSum();
        double percent = creditOffer.getCredit().getInterestRate();
        LocalDate localDate = LocalDate.now();

        double temp = percent / (100 * 12);

        double PaymentPerMount = sum * (temp / (1 - Math.pow(1 + temp, -duration)));
        double t = PaymentPerMount*duration;
        for (int i = 1; i <= duration; i++) {
            double creditSum = (t * (percent / 100) * 30) / 365;
            t = t - PaymentPerMount;

            paymentEvents.add(PaymentEvent.builder()
                    .creditOffer(creditOffer)
                    .creditSum(PaymentPerMount-creditSum)
                    .paymentSum(PaymentPerMount)
                    .interestSum(creditSum)
                    .localDate(localDate.plusMonths(i))
                    .build());
            System.out.println(i);
        }

        return paymentEvents;
    }
}
