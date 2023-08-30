package bank.system.rest.controller;

import bank.system.model.domain.Client;
import bank.system.model.domain.Credit;
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
public class CreditController {
    private StorageDAO<Credit, UUID> storageDAO;

    public CreditController(StorageDAO<Credit, UUID> storageDAO) {
        this.storageDAO = storageDAO;
    }

    @GetMapping("/credits")
    public String getCredits(Model model) {
        List<Credit> creditList = storageDAO.getAll();

        System.out.println(creditList.toString());

        model.addAttribute("credits", creditList);
        return "credits";
    }

    @GetMapping("/credits/new")
    public String getCreatePage(Model model) {
        model.addAttribute("credit", new Client());

        return "updateCredit";
    }

    @GetMapping("/credits/edit/{id}")
    public String getUpdatePage(@PathVariable String id, Model model) {

        Credit credit = storageDAO.findById(UUID.fromString(id));
        model.addAttribute("credit", Objects.requireNonNullElseGet(credit, Credit::new));

        return "updateCredit";
    }

    @GetMapping("/credits/remove/{id}")
    public String remove(@PathVariable String id) {
        storageDAO.removeById(UUID.fromString(id));
        return "redirect:/credits";
    }


    @PostMapping("/credits/new")
    public String merge(Credit credit) {
        if(credit.getId() == null) {
            storageDAO.save(credit);
        } else {
            storageDAO.update(credit);
        }
        return "redirect:/credits";
    }

}
