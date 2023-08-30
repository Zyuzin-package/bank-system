package bank.system.rest.controller;


import bank.system.model.domain.Client;
import bank.system.rest.dao.api.service.api.StorageDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
public class ClientController {
    private StorageDAO<Client, UUID> storageDAO;

    public ClientController(StorageDAO<Client, UUID> storageDAO) {
        this.storageDAO = storageDAO;

    }

    @GetMapping("/clients")
    public String getClients(Model model) {
        List<Client> clientList = storageDAO.getAll();

        model.addAttribute("clients", clientList);
        return "clients";
    }

    @GetMapping("/clients/new")
    public String getCreatePage(Model model) {
        model.addAttribute("client", new Client());

        return "updateClient";
    }

    @GetMapping("/clients/edit/{id}")
    public String getUpdatePage(@PathVariable String id, Model model) {

        Client client = storageDAO.findById(UUID.fromString(id));
        model.addAttribute("client", Objects.requireNonNullElseGet(client, Client::new));

        return "updateClient";
    }

    @GetMapping("/clients/remove/{id}")
    public String remove(@PathVariable String id, Model model) {
        storageDAO.removeById(UUID.fromString(id));
        return "redirect:/clients";
    }

    @PostMapping("/clients/new")
    public String merge(Client client, Model model) {
        if(client.getId() == null) {
            storageDAO.save(client);
        } else {
            storageDAO.update(client);
        }
        return "redirect:/clients";
    }


}
