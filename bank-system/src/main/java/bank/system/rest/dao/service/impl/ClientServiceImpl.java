package bank.system.rest.dao.service.impl;

import bank.system.model.domain.Client;
import bank.system.model.domain.CreditOffer;
import bank.system.rest.exception.EntityNotFoundException;
import bank.system.rest.dao.repository.ClientRepository;
import bank.system.rest.dao.service.api.StorageDAO;
import org.hibernate.annotations.Fetch;
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

    public ClientServiceImpl(
            ClientRepository clientRepository,
            @Lazy CreditOfferServiceImpl creditOfferService
    ) {
        this.clientRepository = clientRepository;
        this.creditOfferService = creditOfferService;
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
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
    public void removeById(UUID uuid) {
        clientRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("Client with id: " + uuid + " not found"));

        CreditOffer creditOffer = creditOfferService.findByClient(uuid);

        if (creditOffer != null) {
            creditOfferService.removeById(creditOffer.getId());
        }

        clientRepository.deleteById(uuid);
    }

}
