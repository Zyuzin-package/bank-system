package bank.system.rest.dao.service.impl;

import bank.system.model.domain.PaymentEvent;
import bank.system.rest.dao.repository.PaymentEventRepository;
import bank.system.rest.dao.service.api.StorageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentEventServiceImpl implements StorageDAO<PaymentEvent,UUID> {

    private final PaymentEventRepository paymentEventRepository;

    @Autowired
    public PaymentEventServiceImpl(PaymentEventRepository paymentEventRepository) {
        this.paymentEventRepository = paymentEventRepository;
    }

    @Override
    public PaymentEvent save(PaymentEvent credit) {
        return paymentEventRepository.save(credit);
    }

    @Override
    public List<PaymentEvent> getAll() {
        return paymentEventRepository.findAll();
    }

    @Override
    public PaymentEvent findById(UUID uuid) {
        return paymentEventRepository.findById(uuid).orElse(null);
    }

    @Override
    public PaymentEvent update(PaymentEvent paymentEvent) {
        PaymentEvent SavedPaymentEvent = paymentEventRepository.findById(paymentEvent.getId()).orElse(null);

        if (SavedPaymentEvent == null) {
            throw new RuntimeException("Payment event not found");
        }

        return paymentEventRepository.save(paymentEvent);
    }

    @Override
    public boolean remove(PaymentEvent paymentEvent) {
        paymentEventRepository.delete(paymentEvent);
        return true;
    }

    @Override
    public boolean removeById(UUID uuid) {
        paymentEventRepository.deleteById(uuid);
        return true;
    }

    public List<PaymentEvent> getPaymentEventByOfferId(String id){
        return paymentEventRepository.getPaymentEventByOfferId(UUID.fromString(id));
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<PaymentEvent> bulkSave(List<PaymentEvent> paymentEvents){
        List<PaymentEvent> saved = new ArrayList<>();
        for (PaymentEvent p: paymentEvents){
            saved.add(save(p));
        }
        return saved;
    }

}
