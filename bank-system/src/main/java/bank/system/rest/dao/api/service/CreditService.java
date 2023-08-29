package bank.system.rest.dao.api.service;

import bank.system.model.Credit;
import bank.system.model.CreditOffer;

import java.util.List;
import java.util.UUID;

public interface CreditService {
    Credit save(Credit credit);
    List<Credit> getAll();
    Credit findById(UUID uuid);
    Credit update(Credit credit);
    boolean remove(Credit credit);
    boolean removeById(UUID uuid);
}
