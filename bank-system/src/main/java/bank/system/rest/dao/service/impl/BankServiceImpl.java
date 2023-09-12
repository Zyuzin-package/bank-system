package bank.system.rest.dao.service.impl;

import bank.system.model.domain.Bank;
import bank.system.rest.dao.repository.BankRepository;
import bank.system.rest.dao.service.api.StorageDAO;
import bank.system.rest.exception.EntityNotFoundException;
import bank.system.rest.exception.ServerException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BankServiceImpl implements StorageDAO<Bank, UUID> {
    private final BankRepository bankRepository;

    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public Bank save(Bank bank) {
        return bankRepository.save(bank);
    }

    @Override
    public List<Bank> getAll() {
        return bankRepository.findAll();
    }

    @Override
    public Bank findById(UUID id) {
        return bankRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bank with id: " + id + " not found"));
    }

    @Override
    public Bank update(Bank bank) {
        Bank savedBank = bankRepository.findById(bank.getId()).orElseThrow(() -> new EntityNotFoundException("Bank with id: " + bank.getId() + " not found"));

        bank.setId(savedBank.getId());

        return bankRepository.save(bank);
    }

    @Override
    public void remove(Bank bank) {
        bankRepository.delete(bank);
    }

    @Override
    public void removeById(UUID id) throws ServerException {
        bankRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bank with id: " + id + " not found"));

        bankRepository.deleteById(id);
    }
}
