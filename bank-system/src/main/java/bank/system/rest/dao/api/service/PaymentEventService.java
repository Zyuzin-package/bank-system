package bank.system.rest.dao.api.service;


import bank.system.model.PaymentEvent;

import java.util.List;
import java.util.UUID;

public interface PaymentEventService {
    PaymentEvent save(PaymentEvent credit);
    List<PaymentEvent> getAll();
    PaymentEvent findById(UUID uuid);
    PaymentEvent update(PaymentEvent credit);
    boolean remove(PaymentEvent credit);
    boolean removeById(UUID uuid);
}
