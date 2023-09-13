package bank.system.rest.dao.service.impl;

import bank.system.model.domain.Bank;
import bank.system.model.domain.Client;
import bank.system.model.domain.CreditOffer;
import bank.system.rest.exception.EntityNotFoundException;
import bank.system.rest.dao.repository.ClientRepository;
import bank.system.rest.dao.service.api.StorageDAO;
import bank.system.rest.exception.ServerException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ClientServiceImpl implements StorageDAO<Client, UUID> {

    private final ClientRepository clientRepository;
    @Lazy
    private final CreditOfferServiceImpl creditOfferService;
    private final BankServiceImpl bankService;

    public ClientServiceImpl(ClientRepository clientRepository, @Lazy CreditOfferServiceImpl creditOfferService, BankServiceImpl bankService) {
        this.clientRepository = clientRepository;
        this.creditOfferService = creditOfferService;
        this.bankService = bankService;
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }
    public Client updateClientInBank(Client client, UUID bankId) {
        Bank bank = bankService.findById(bankId);
        Client savedClient;

        if (client.getId() != null) {
            savedClient = update(client);
        } else {
            savedClient = save(client);
        }

        Client clientInBank = bank.getClientList().stream().filter(client1 -> client1.getId() == client.getId()).findFirst().orElse(null);

        if (!savedClient.equals(clientInBank)) {
            bank.getClientList().add(savedClient);
            bankService.update(bank);
        }

        return savedClient;
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(UUID uuid) throws EntityNotFoundException {
        return clientRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("Client with id: " + uuid + " not found"));
    }

    @Override
    public Client update(Client client) {
        Client savedClient = clientRepository.findById(client.getId()).orElseThrow(() -> new EntityNotFoundException("Client with id: " + client.getId() + " not found"));
        client.setId(savedClient.getId());

        return clientRepository.save(client);
    }

    @Override
    public void remove(Client client) {
        clientRepository.delete(client);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void removeById(UUID uuid) throws ServerException {
        Client oldClient = clientRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("Client with id: " + uuid + " not found"));
        CreditOffer creditOffer = creditOfferService.findByClient(uuid);

        if (creditOffer != null) {
            throw new ServerException("There is a credit offer for this client");
        }
        Bank bank = bankService.findByClient(oldClient);
        bank.getClientList().removeIf(client -> client.getId() == oldClient.getId());

        bankService.update(bank);

        clientRepository.deleteById(uuid);
    }

}
