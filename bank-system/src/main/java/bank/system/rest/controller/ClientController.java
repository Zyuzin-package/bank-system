package bank.system.rest.controller;


import bank.system.model.domain.Client;
import bank.system.model.entity_treatment.validators.ClientValidatorImpl;
import bank.system.model.entity_treatment.validators.UUIDValidatorImpl;
import bank.system.rest.dao.service.impl.BankServiceImpl;
import bank.system.rest.dao.service.impl.ClientServiceImpl;
import bank.system.rest.exception.ServerException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
public class ClientController {

    private final ClientServiceImpl clientServiceImpl;
    private final BankServiceImpl bankServiceImpl;

    private final UUIDValidatorImpl uuidValidatorImpl;
    private final ClientValidatorImpl clientValidation;

    public ClientController(ClientServiceImpl clientServiceImpl, BankServiceImpl bankServiceImpl, UUIDValidatorImpl uuidValidatorImpl, ClientValidatorImpl clientValidation) {
        this.clientServiceImpl = clientServiceImpl;
        this.bankServiceImpl = bankServiceImpl;
        this.uuidValidatorImpl = uuidValidatorImpl;
        this.clientValidation = clientValidation;
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
        model.addAttribute("banks", bankServiceImpl.getAll());
        return "updateClient";
    }

    @GetMapping("/clients/edit/{id}")
    public String getUpdatePage(@PathVariable String id, Model model) {
        uuidValidatorImpl.validate(id);

        Client client = clientServiceImpl.findById(UUID.fromString(id));

        model.addAttribute("client", Objects.requireNonNullElseGet(client, Client::new));
        model.addAttribute("banks", bankServiceImpl.getAll());
        return "updateClient";
    }

    @GetMapping("/clients/remove/{id}")
    public String remove(@PathVariable String id, Model model) throws ServerException {
        uuidValidatorImpl.validate(id);

        clientServiceImpl.removeById(UUID.fromString(id));

        return "redirect:/clients";
    }

    @PostMapping("/clients/new")
    public String merge(Client client, @RequestParam(name = "bank") String bankId) throws ServerException {
        clientValidation.validate(client);

        clientServiceImpl.updateClientInBank(client, UUID.fromString(bankId));

        return "redirect:/clients";
    }
}
