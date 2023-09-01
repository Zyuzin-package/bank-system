package bank.system.rest.dao.repository;

import bank.system.model.domain.CreditOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CreditOfferRepository extends JpaRepository<CreditOffer, UUID> {
    CreditOffer findCreditOfferByClientId(UUID clientId);
    CreditOffer findCreditOfferByCreditId(UUID creditId);
}
