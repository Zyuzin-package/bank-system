package bank.system.rest.controller;

import bank.system.model.domain.Bank;
import bank.system.model.domain.Client;
import bank.system.rest.Validator;
import bank.system.rest.dao.service.impl.BankServiceImpl;
import bank.system.rest.exception.ServerException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
public class BankController {
    private final BankServiceImpl bankServiceImpl;
    private final Validator validator;

    public BankController(BankServiceImpl bankServiceImpl, Validator validator) {
        this.bankServiceImpl = bankServiceImpl;
        this.validator = validator;
    }

    @GetMapping("/banks")
    public String getClients(Model model) {
        List<Bank> banks = bankServiceImpl.getAll();
        model.addAttribute("banks", banks);
        return "banks";
    }

    @GetMapping("/banks/new")
    public String getCreatePage(Model model) {
        model.addAttribute("bank", new Bank());
        model.addAttribute("banks", bankServiceImpl.getAll());
        return "updateBank";
    }

    @GetMapping("/banks/{id}")
    public String getBankDetails(@PathVariable String id,Model model) {

        Bank bank = bankServiceImpl.findById(UUID.fromString(id));

        model.addAttribute("bank",bank);
        model.addAttribute("credits",bank.getCreditList());
        model.addAttribute("clients",bank.getClientList());

        return "bankDetails";
    }

    @GetMapping("/banks/edit/{id}")
    public String getUpdatePage(@PathVariable String id, Model model) {
        validator.uuidValidator(id);

        Bank bank = bankServiceImpl.findById(UUID.fromString(id));
        model.addAttribute("bank", Objects.requireNonNullElseGet(bank, Bank::new));
        return "updateBank";
    }

    @GetMapping("/banks/remove/{id}")
    public String remove(@PathVariable String id, Model model) throws ServerException {
        validator.uuidValidator(id);

        bankServiceImpl.removeById(UUID.fromString(id));
        return "redirect:/banks";
    }

    @PostMapping("/banks/new")
    public String merge(Bank bank) {
//        validator.clientValidation(client);

        if (bank.getId() == null) {
            bankServiceImpl.save(bank);
        } else {
            bankServiceImpl.update(bank);
        }

        return "redirect:/banks";
    }
}
