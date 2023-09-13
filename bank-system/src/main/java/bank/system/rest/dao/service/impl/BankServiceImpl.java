package bank.system.rest.dao.service.impl;

import bank.system.model.domain.Bank;
import bank.system.model.domain.Client;
import bank.system.model.domain.Credit;
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

    public Bank findByClient(Client client) {
        return bankRepository.findBankByClientListContains(client);
    }

    public Bank findByCredit(Credit credit) {
        return bankRepository.findBankByCreditListContains(credit);
    }

    @Override
    public Bank update(Bank bank) {
        Bank savedBank = bankRepository.findById(bank.getId()).orElseThrow(() -> new EntityNotFoundException("Bank with id: " + bank.getId() + " not found"));

        bank.setId(savedBank.getId());

        return bankRepository.save(bank);
    }

    @Override
    public void remove(Bank bank) throws ServerException {
        Bank oldBank = findById(bank.getId());
        if (!oldBank.getClientList().isEmpty()) {
            throw new ServerException("Bank has clients");
        } else if (!oldBank.getCreditList().isEmpty()) {
            throw new ServerException("Bank has credits");
        }

        bankRepository.delete(bank);
    }

    @Override
    public void removeById(UUID id) throws ServerException {
        Bank oldBank = bankRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bank with id: " + id + " not found"));

        if (!oldBank.getClientList().isEmpty()){
            throw new ServerException("Bank has clients");
        } else if (!oldBank.getCreditList().isEmpty()){
            throw new ServerException("Bank has credits");
        }

        bankRepository.deleteById(id);
    }
}
