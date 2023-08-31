package bank.system.rest.dao.service.impl;

import bank.system.model.domain.Client;
import bank.system.rest.dao.repository.ClientRepository;
import bank.system.rest.dao.service.api.StorageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientServiceImpl implements StorageDAO<Client,UUID> {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
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
        System.out.println("\n\n"+ client);

        Client savedClient = clientRepository.findByPassportID(client.getPassportID());

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
    public boolean removeById(UUID uuid) {
        clientRepository.deleteById(uuid);
        return true;
    }

}
