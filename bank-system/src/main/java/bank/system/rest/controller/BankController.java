package bank.system.rest.controller;

import bank.system.model.domain.Bank;
import bank.system.model.entity_treatment.validators.BankValidatorImpl;
import bank.system.model.entity_treatment.validators.UUIDValidatorImpl;
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
    private final BankValidatorImpl bankValidatorImpl;
    private final UUIDValidatorImpl uuidValidatorImpl;

    public BankController(BankServiceImpl bankServiceImpl, BankValidatorImpl bankValidatorImpl, UUIDValidatorImpl uuidValidatorImpl) {
        this.bankServiceImpl = bankServiceImpl;
        this.bankValidatorImpl = bankValidatorImpl;
        this.uuidValidatorImpl = uuidValidatorImpl;
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
    public String getBankDetails(@PathVariable String id, Model model) {
        Bank bank = bankServiceImpl.findById(UUID.fromString(id));

        model.addAttribute("bank", bank);
        model.addAttribute("credits", bank.getCreditList());
        model.addAttribute("clients", bank.getClientList());

        return "bankDetails";
    }

    @GetMapping("/banks/edit/{id}")
    public String getUpdatePage(@PathVariable String id, Model model) {
        uuidValidatorImpl.validate(id);

        Bank bank = bankServiceImpl.findById(UUID.fromString(id));
        model.addAttribute("bank", Objects.requireNonNullElseGet(bank, Bank::new));
        return "updateBank";
    }

    @GetMapping("/banks/remove/{id}")
    public String remove(@PathVariable String id) throws ServerException {
        uuidValidatorImpl.validate(id);

        bankServiceImpl.removeById(UUID.fromString(id));
        return "redirect:/banks";
    }

    @PostMapping("/banks/new")
    public String merge(Bank bank) {
        bankValidatorImpl.validate(bank);

        if (bank.getId() == null) {
            bankServiceImpl.save(bank);
        } else {
            bankServiceImpl.update(bank);
        }

        return "redirect:/banks";
    }
}
