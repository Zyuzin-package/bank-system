package bank.system.rest.dao.api.service.impl;

import bank.system.model.Credit;
import bank.system.rest.dao.api.repository.CreditRepository;
import bank.system.rest.dao.api.service.api.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;

    @Autowired
    public CreditServiceImpl(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    @Override
    public Credit save(Credit credit) {
        return creditRepository.save(credit);
    }

    @Override
    public List<Credit> getAll() {
        return creditRepository.findAll();
    }

    @Override
    public Credit findById(UUID uuid) {
        return creditRepository.findById(uuid).orElse(null);
    }

    @Override
    public Credit update(Credit credit) {
        Credit savedCredit = creditRepository.findById(credit.getId()).orElse(null);

        if (savedCredit != null) {
            throw new RuntimeException("Credit with id " + savedCredit.getId() + " not found");
        }

        return creditRepository.save(credit);
    }

    @Override
    public boolean remove(Credit credit) {
        creditRepository.delete(credit);
        return true;
    }

    @Override
    public boolean removeById(UUID uuid) {
        creditRepository.deleteById(uuid);
        return true;
    }
}
