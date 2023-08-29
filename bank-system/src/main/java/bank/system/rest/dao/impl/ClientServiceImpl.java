package bank.system.rest.dao.impl;

import bank.system.model.Client;
import bank.system.rest.dao.api.repository.ClientRepository;
import bank.system.rest.dao.api.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {

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
        Client savedClient = clientRepository.findById(client.getId()).orElse(null);

        if (savedClient != null) {
            throw new RuntimeException("Client with id " + savedClient.getId() + " not found");
        }

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
