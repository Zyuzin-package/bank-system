package bank.system.rest.controller;

import bank.system.model.domain.Credit;
import bank.system.rest.dao.service.impl.BankServiceImpl;
import bank.system.rest.exception.ServerException;
import bank.system.rest.exception.ValidationException;
import bank.system.rest.Validator;
import bank.system.rest.dao.service.impl.CreditServiceImpl;
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
public class CreditController {
    private final CreditServiceImpl creditServiceImpl;
    private final Validator validator;
    private final BankServiceImpl bankServiceImpl;

    public CreditController(CreditServiceImpl creditServiceImpl, Validator validator, BankServiceImpl bankServiceImpl) {
        this.creditServiceImpl = creditServiceImpl;
        this.validator = validator;
        this.bankServiceImpl = bankServiceImpl;
    }

    @GetMapping("/credits")
    public String getCredits(Model model) {
        List<Credit> creditList = creditServiceImpl.getAll();

        model.addAttribute("credits", creditList);
        return "credits";
    }

    @GetMapping("/credits/new")
    public String getCreatePage(Model model) {
        model.addAttribute("credit", new Credit());
        model.addAttribute("banks", bankServiceImpl.getAll());

        return "updateCredit";
    }

    @GetMapping("/credits/edit/{id}")
    public String getUpdatePage(@PathVariable String id, Model model) {
        validator.uuidValidator(id);

        Credit credit = creditServiceImpl.findById(UUID.fromString(id));

        model.addAttribute("credit", Objects.requireNonNullElseGet(credit, Credit::new));
        model.addAttribute("banks", bankServiceImpl.getAll());
        return "updateCredit";
    }

    @GetMapping("/credits/remove/{id}")
    public String remove(@PathVariable String id) throws ServerException {
        validator.uuidValidator(id);

        creditServiceImpl.removeById(UUID.fromString(id));
        return "redirect:/credits";
    }


    @PostMapping("/credits/new")
    public String merge(Credit credit, @RequestParam(name = "bank") String bankId) {
        validator.creditValidation(credit);

        creditServiceImpl.updateCreditInBank(credit, UUID.fromString(bankId));
        return "redirect:/credits";
    }

}
