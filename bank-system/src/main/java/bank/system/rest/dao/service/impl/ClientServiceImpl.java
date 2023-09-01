package bank.system.rest.dao.service.impl;

import bank.system.model.domain.Client;
import bank.system.model.domain.CreditOffer;
import bank.system.rest.dao.repository.ClientRepository;
import bank.system.rest.dao.service.api.StorageDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ClientServiceImpl implements StorageDAO<Client, UUID> {

    private final ClientRepository clientRepository;
    private final CreditOfferServiceImpl creditOfferService;


    public ClientServiceImpl(ClientRepository clientRepository, CreditOfferServiceImpl creditOfferService) {
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
    public Client findById(UUID uuid) {
        return clientRepository.findById(uuid).orElse(null);
    }

    @Override
    public Client update(Client client) {

        Client savedClient = clientRepository.findById(client.getId()).orElse(null);

        if (savedClient == null) {
            throw new RuntimeException("Client not found");
        }

        client.setId(savedClient.getId());

        return clientRepository.save(client);
    }

    @Override
    public boolean remove(Client client) {
        clientRepository.delete(client);
        return true;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public boolean removeById(UUID uuid) {
        CreditOffer creditOffer = creditOfferService.findByClient(uuid);

        if (creditOffer != null) {
            creditOfferService.removeById(creditOffer.getId());
        }

        clientRepository.deleteById(uuid);
        return true;
    }

}
