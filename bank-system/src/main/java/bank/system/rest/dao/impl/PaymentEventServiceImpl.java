package bank.system.rest.dao.impl;

import bank.system.model.Client;
import bank.system.model.PaymentEvent;
import bank.system.rest.dao.api.repository.PaymentEventRepository;
import bank.system.rest.dao.api.service.PaymentEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentEventServiceImpl implements PaymentEventService {

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

        if (SavedPaymentEvent != null) {
            throw new RuntimeException("Payment event with id " + paymentEvent.getId() + " not found");
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
}
