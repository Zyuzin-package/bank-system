package bank.system.rest.dao.api.repository;

import bank.system.model.PaymentEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentEventRepository extends JpaRepository<PaymentEvent, UUID> {
}
