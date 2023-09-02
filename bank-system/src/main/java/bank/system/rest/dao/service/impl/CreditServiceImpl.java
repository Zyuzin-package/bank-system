package bank.system.rest.dao.service.impl;

import bank.system.model.domain.Credit;
import bank.system.model.domain.CreditOffer;
import bank.system.rest.exception.EntityNotFoundException;
import bank.system.rest.dao.repository.CreditRepository;
import bank.system.rest.dao.service.api.StorageDAO;
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
    @Lazy
    private final CreditOfferServiceImpl creditOfferService;
    @Autowired
    public CreditServiceImpl(CreditRepository creditRepository, CreditOfferServiceImpl creditOfferService) {
        this.creditRepository = creditRepository;
        this.creditOfferService = creditOfferService;
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

        if (savedCredit == null) {
            throw new EntityNotFoundException("Credit with id: " + credit.getId() + " not found");
        }

        return creditRepository.save(credit);
    }

    @Override
    public void remove(Credit credit) {
        creditRepository.delete(credit);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void removeById(UUID uuid) {
        CreditOffer creditOffer = creditOfferService.findByCredit(uuid);

        if (creditOffer != null) {
            creditOfferService.removeById(creditOffer.getId());
        }

        creditRepository.deleteById(uuid);
    }
}
