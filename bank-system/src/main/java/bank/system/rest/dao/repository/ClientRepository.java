package bank.system.rest.dao.repository;

import bank.system.model.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

}
