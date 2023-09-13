package bank.system.rest.controller;

import bank.system.model.domain.Credit;
import bank.system.model.entity_treatment.validators.CreditValidatorImpl;
import bank.system.model.entity_treatment.validators.UUIDValidatorImpl;
import bank.system.rest.dao.service.impl.BankServiceImpl;
import bank.system.rest.exception.ServerException;
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
    private final BankServiceImpl bankServiceImpl;
    private final UUIDValidatorImpl uuidValidatorImpl;
    private final CreditValidatorImpl creditValidatorImpl;

    public CreditController(CreditServiceImpl creditServiceImpl, BankServiceImpl bankServiceImpl, UUIDValidatorImpl uuidValidatorImpl, CreditValidatorImpl creditValidatorImpl) {
        this.creditServiceImpl = creditServiceImpl;
        this.bankServiceImpl = bankServiceImpl;
        this.uuidValidatorImpl = uuidValidatorImpl;
        this.creditValidatorImpl = creditValidatorImpl;
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
        uuidValidatorImpl.validate(id);

        Credit credit = creditServiceImpl.findById(UUID.fromString(id));

        model.addAttribute("credit", Objects.requireNonNullElseGet(credit, Credit::new));
        model.addAttribute("banks", bankServiceImpl.getAll());
        return "updateCredit";
    }

    @GetMapping("/credits/remove/{id}")
    public String remove(@PathVariable String id) throws ServerException {
        uuidValidatorImpl.validate(id);

        creditServiceImpl.removeById(UUID.fromString(id));
        return "redirect:/credits";
    }


    @PostMapping("/credits/new")
    public String merge(Credit credit, @RequestParam(name = "bank") String bankId) {
        creditValidatorImpl.validate(credit);

        creditServiceImpl.updateCreditInBank(credit, UUID.fromString(bankId));
        return "redirect:/credits";
    }

}
