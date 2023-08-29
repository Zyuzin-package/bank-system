package bank.system.rest.dao.api.repository;

import bank.system.model.CreditOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CreditOfferRepository extends JpaRepository<CreditOffer, UUID> {
}
