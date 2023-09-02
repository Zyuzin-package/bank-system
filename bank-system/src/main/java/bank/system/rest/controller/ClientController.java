package bank.system.rest.controller;


import bank.system.model.domain.Client;
import bank.system.rest.exception.ValidationException;
import bank.system.rest.Validator;
import bank.system.rest.dao.service.impl.ClientServiceImpl;
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
    private final ClientServiceImpl clientServiceImpl;

    private final Validator validator;

    public ClientController(ClientServiceImpl clientServiceImpl, Validator validator) {
        this.clientServiceImpl = clientServiceImpl;
        this.validator = validator;
    }

    @GetMapping("/clients")
    public String getClients(Model model) {
        List<Client> clientList = clientServiceImpl.getAll();

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
        validator.uuidValidator(id);

        Client client = clientServiceImpl.findById(UUID.fromString(id));
        model.addAttribute("client", Objects.requireNonNullElseGet(client, Client::new));
        return "updateClient";
    }

    @GetMapping("/clients/remove/{id}")
    public String remove(@PathVariable String id, Model model) {
        validator.uuidValidator(id);

        clientServiceImpl.removeById(UUID.fromString(id));
        return "redirect:/clients";
    }

    @PostMapping("/clients/new")
    public String merge(Client client, Model model) {
        validator.clientValidation(client);

        if (client.getId() == null) {
            clientServiceImpl.save(client);
        } else {
            clientServiceImpl.update(client);
        }
        return "redirect:/clients";
    }
}
