package bank.system.rest.dao.service.impl;

import bank.system.model.domain.Bank;
import bank.system.model.domain.Client;
import bank.system.model.domain.Credit;
import bank.system.model.domain.CreditOffer;
import bank.system.rest.dao.repository.BankRepository;
import bank.system.rest.exception.EntityNotFoundException;
import bank.system.rest.dao.repository.CreditRepository;
import bank.system.rest.dao.service.api.StorageDAO;
import bank.system.rest.exception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CreditServiceImpl implements StorageDAO<Credit, UUID> {

    private final CreditRepository creditRepository;
    private final BankServiceImpl bankService;
    @Lazy
    private final CreditOfferServiceImpl creditOfferService;

    @Autowired
    public CreditServiceImpl(CreditRepository creditRepository, BankServiceImpl bankService, CreditOfferServiceImpl creditOfferService) {
        this.creditRepository = creditRepository;
        this.bankService = bankService;
        this.creditOfferService = creditOfferService;
    }

    @Override
    public Credit save(Credit credit) {
        return creditRepository.save(credit);
    }

    public Credit updateCreditInBank(Credit credit, UUID bankId) {
        Bank bank = bankService.findById(bankId);
        Credit savedCredit;

        if (credit.getId() != null) {
            savedCredit = update(credit);
        } else {
            savedCredit = save(credit);
        }

        Credit creditInBank = bank.getCreditList().stream().filter(c -> c.getId() == credit.getId()).findFirst().orElse(null);

        if (!savedCredit.equals(creditInBank)) {
            bank.getCreditList().add(savedCredit);
            bankService.update(bank);
        }

        return savedCredit;
    }

    @Override
    public List<Credit> getAll() {
        return creditRepository.findAll();
    }

    @Override
    public Credit findById(UUID uuid) {
        return creditRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("Client with id: " + uuid + " not found"));
    }

    @Override
    public Credit update(Credit credit) {
        Credit savedCredit = creditRepository.findById(credit.getId()).orElseThrow(() -> new EntityNotFoundException("Credit with id: " + credit.getId() + " not found"));
        credit.setId(savedCredit.getId());

        return creditRepository.save(credit);
    }

    @Override
    public void remove(Credit credit) {
        creditRepository.delete(credit);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void removeById(UUID uuid) throws ServerException {
        Credit oldCredit = creditRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("Credit with id: " + uuid + " not found"));
        CreditOffer creditOffer = creditOfferService.findByCredit(uuid);

        if (creditOffer != null) {
            throw new ServerException("There is a credit offer for this credit");
        }

        Bank bank = bankService.findByCredit(oldCredit);
        bank.getCreditList().removeIf(credit -> credit.getId() == oldCredit.getId());

        bankService.update(bank);

        creditRepository.deleteById(uuid);
    }
}
