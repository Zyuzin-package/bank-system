package bank.system.rest.dao.service.impl;

import bank.system.model.domain.Client;
import bank.system.model.domain.Credit;
import bank.system.model.domain.CreditOffer;
import bank.system.model.domain.PaymentEvent;
import bank.system.rest.exception.EntityNotFoundException;
import bank.system.rest.dao.repository.CreditOfferRepository;
import bank.system.rest.dao.service.api.StorageDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CreditOfferServiceImpl implements StorageDAO<CreditOffer, UUID> {


    private final CreditOfferRepository creditOfferRepository;
    private final PaymentEventServiceImpl paymentEventService;

    private final CreditServiceImpl creditService;
    private final ClientServiceImpl clientService;

    /**
     * variable responsible for rounding in the method rounding(double d)
     * which is used in the method {@link #buildPaymentGraph(CreditOffer creditOffer)}
     */
    @Value("${bank.credit-offer.scale}")
    private double scale;

    public CreditOfferServiceImpl(
            CreditOfferRepository creditOfferRepository,
            PaymentEventServiceImpl paymentEventService,
            @Lazy CreditServiceImpl creditService,
            @Lazy ClientServiceImpl clientService
    ) {
        this.creditOfferRepository = creditOfferRepository;
        this.paymentEventService = paymentEventService;
        this.creditService = creditService;
        this.clientService = clientService;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public CreditOffer save(CreditOffer creditOffer) {
        creditService.save(creditOffer.getCredit());
        clientService.save(creditOffer.getClient());

        List<PaymentEvent> paymentGraph = buildPaymentGraph(creditOffer);

        List<PaymentEvent> savedGraph = paymentEventService.bulkSave(paymentGraph);
        creditOffer.setPaymentEventList(savedGraph);


        return creditOfferRepository.save(creditOffer);
    }

    @Override
    public List<CreditOffer> getAll() {
        return creditOfferRepository.findAll();
    }

    @Override
    public CreditOffer findById(UUID uuid) {
        return creditOfferRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("Client with id: " + uuid + " not found"));
    }

    public CreditOffer findByClient(UUID uuid) {
        CreditOffer offer = creditOfferRepository.findCreditOfferByClientId(uuid);
        if (offer == null) {
            throw new EntityNotFoundException("CreditOffer with client id: " + uuid + " not found");
        }
        return offer;
    }

    public CreditOffer findByCredit(UUID uuid) {
        CreditOffer offer = creditOfferRepository.findCreditOfferByCreditId(uuid);
        if (offer == null) {
            throw new EntityNotFoundException("CreditOffer by with credit id: " + uuid + " not found");
        }
        return offer;
    }

    @Override
    public CreditOffer update(CreditOffer creditOffer) {
        CreditOffer savedOffer = creditOfferRepository.findById(creditOffer.getId()).orElse(null);

        if (savedOffer == null) {
            throw new EntityNotFoundException("CreditOffer by with credit id: " + creditOffer.getId() + " not found");
        }

        return creditOfferRepository.save(creditOffer);
    }

    @Override
    public void remove(CreditOffer creditOffer) {
        creditOfferRepository.delete(creditOffer);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void removeById(UUID uuid) {
        CreditOffer creditOffer = findById(uuid);
        if (creditOffer == null) {
            throw new EntityNotFoundException("CreditOffer by with id: " + uuid + " not found");
        }

        for (PaymentEvent p : creditOffer.getPaymentEventList()) {
            paymentEventService.removeById(p.getId());
        }

        creditOfferRepository.deleteById(uuid);
    }

    /**
     * Method that calculates the payment schedule for an annuity payment
     * @param creditOffer - the entity on the basis of which you need to build a payment schedule
     * @return list for further save
     */
    private List<PaymentEvent> buildPaymentGraph(CreditOffer creditOffer) {
        List<PaymentEvent> paymentEvents = new ArrayList<>();
        int duration = creditOffer.getDuration();
        double sum = creditOffer.getPaymentSum();
        double percent = creditOffer.getCredit().getInterestRate();
        LocalDate localDate = LocalDate.now();

        double temp = percent / (100 * 12);

        double PaymentPerMount = sum * (temp / (1 - Math.pow(1 + temp, -duration)));
        double t = PaymentPerMount * duration;
        for (int i = 1; i <= duration; i++) {
            double creditSum = (t * (percent / 100) * 30) / 365;
            t = t - PaymentPerMount;

            paymentEvents.add(PaymentEvent.builder()
                    .creditOffer(creditOffer)
                    .creditSum(rounding(PaymentPerMount - creditSum))
                    .paymentSum(rounding(PaymentPerMount))
                    .interestSum(rounding(creditSum))
                    .localDate(localDate.plusMonths(i))
                    .build());
        }

        return paymentEvents;
    }

    /**
     * Rounding method, based on value {@link #scale}
     * @param d - number to be rounded
     * @return - rounded number
     */
    private double rounding(double d) {
        return Math.round(d * scale) / scale;
    }
}
