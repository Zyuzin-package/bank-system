package bank.system.rest.dao.repository;

import bank.system.model.domain.Bank;
import bank.system.model.domain.Client;
import bank.system.model.domain.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankRepository extends JpaRepository<Bank, UUID> {
    Bank findBankByClientListContains(Client client);
    Bank findBankByCreditListContains(Credit credit);
}
