package bank.system.rest.dao.api.service.impl;

import bank.system.model.CreditOffer;
import bank.system.rest.dao.api.repository.CreditOfferRepository;
import bank.system.rest.dao.api.service.api.CreditOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CreditOfferServiceImpl implements CreditOfferService {


    private final CreditOfferRepository creditOfferRepository;

    @Autowired
    public CreditOfferServiceImpl(CreditOfferRepository creditOfferRepository) {
        this.creditOfferRepository = creditOfferRepository;
    }

    @Override
    public CreditOffer save(CreditOffer creditOffer) {
        return creditOfferRepository.save(creditOffer);
    }

    @Override
    public List<CreditOffer> getAll() {
        return creditOfferRepository.findAll();
    }

    @Override
    public CreditOffer findById(UUID uuid) {
        return creditOfferRepository.findById(uuid).orElse(null);
    }

    @Override
    public CreditOffer update(CreditOffer creditOffer) {
        CreditOffer savedOffer = creditOfferRepository.findById(creditOffer.getId()).orElse(null);

        if (savedOffer != null) {
            throw new RuntimeException("CreditOffer with id " + savedOffer.getId() + " not found");
        }

        return creditOfferRepository.save(creditOffer);
    }

    @Override
    public boolean remove(CreditOffer creditOffer) {
        creditOfferRepository.delete(creditOffer);
        return true;
    }

    @Override
    public boolean removeById(UUID uuid) {
        creditOfferRepository.deleteById(uuid);
        return true;
    }
}
