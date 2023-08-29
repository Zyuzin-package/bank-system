package bank.system.rest.dao.api.service.api;



import bank.system.model.CreditOffer;

import java.util.List;
import java.util.UUID;

public interface CreditOfferService {
    CreditOffer save(CreditOffer creditOffer);
    List<CreditOffer> getAll();
    CreditOffer findById(UUID uuid);
    CreditOffer update(CreditOffer creditOffer);
    boolean remove(CreditOffer creditOffer);
    boolean removeById(UUID uuid);

}
