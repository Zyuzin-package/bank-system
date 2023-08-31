package bank.system.rest.dao.repository;

import bank.system.model.domain.PaymentEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface PaymentEventRepository extends JpaRepository<PaymentEvent, UUID> {
    @Modifying
    @Query(value = "Select * from payment_event where credit_offer_id  =:creditOfferId", nativeQuery = true)
    @Transactional
    List<PaymentEvent> getPaymentEventByOfferId(@Param("creditOfferId")UUID creditOfferId);
}
