package bank.system.rest.dao.api.repository;

import bank.system.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CreditRepository extends JpaRepository<Credit, UUID> {
}