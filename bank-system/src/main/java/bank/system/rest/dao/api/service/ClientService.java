package bank.system.rest.dao.api.service;

import bank.system.model.Client;

import java.util.List;
import java.util.UUID;

public interface ClientService {
    Client save(Client client);
    List<Client> getAll();
    Client findById(UUID uuid);
    Client update(Client client);
    boolean remove(Client client);
    boolean removeById(UUID uuid);

}
