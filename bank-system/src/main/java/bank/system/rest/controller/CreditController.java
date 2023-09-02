package bank.system.rest.controller;

import bank.system.model.domain.Credit;
import bank.system.model.exception.ValidationException;
import bank.system.rest.Validator;
import bank.system.rest.dao.service.api.StorageDAO;
import bank.system.rest.dao.service.impl.CreditServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
public class CreditController {
    private final CreditServiceImpl creditServiceImpl;
    private final Validator validator;

    public CreditController(CreditServiceImpl creditServiceImpl, Validator validator) {
        this.creditServiceImpl = creditServiceImpl;
        this.validator = validator;
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

        return "updateCredit";
    }

    @GetMapping("/credits/edit/{id}")
    public String getUpdatePage(@PathVariable String id, Model model) {
        String validErrors = validator.uuidValidator(id);
        if (validErrors != null) {
            throw new ValidationException(validErrors);
        }

        Credit credit = creditServiceImpl.findById(UUID.fromString(id));
        model.addAttribute("credit", Objects.requireNonNullElseGet(credit, Credit::new));

        return "updateCredit";
    }

    @GetMapping("/credits/remove/{id}")
    public String remove(@PathVariable String id) {
        String validErrors = validator.uuidValidator(id);
        if (validErrors != null) {
            throw new ValidationException(validErrors);
        }

        creditServiceImpl.removeById(UUID.fromString(id));
        return "redirect:/credits";
    }


    @PostMapping("/credits/new")
    public String merge(Credit credit) {
        String validErrors = validator.creditValidation(credit);
        if (validErrors != null) {
            throw new ValidationException(validErrors);
        }

        if(credit.getId() == null) {
            creditServiceImpl.save(credit);
        } else {
            creditServiceImpl.update(credit);
        }
        return "redirect:/credits";
    }

}
