package bank.system.rest.controller;

import bank.system.model.domain.CreditOffer;
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
public class CreditOfferController {

    private StorageDAO<CreditOffer, UUID> storageDAO;

    public CreditOfferController(StorageDAO<CreditOffer, UUID> storageDAO) {
        this.storageDAO = storageDAO;
    }

    @GetMapping("/creditsOffers")
    public String getCreditsOffers(Model model) {
        List<CreditOffer> creditOffers = storageDAO.getAll();

        model.addAttribute("creditOffers", creditOffers);
        return "creditsOffers";
    }

    @GetMapping("/creditsOffers/new")
    public String getCreatePage(Model model) {
        model.addAttribute("creditOffer", new CreditOffer());

        return "updateCreditOffer";
    }

    @GetMapping("/creditsOffers/edit/{id}")
    public String getUpdatePage(@PathVariable String id, Model model) {
        CreditOffer creditOffer = storageDAO.findById(UUID.fromString(id));
        model.addAttribute("creditOffer", Objects.requireNonNullElseGet(creditOffer, CreditOffer::new));

        return "updateCreditOffer";
    }

    @GetMapping("/creditsOffers/remove/{id}")
    public String remove(@PathVariable String id, Model model) {
        storageDAO.removeById(UUID.fromString(id));
        return "redirect:/creditsOffers";
    }

    @PostMapping("/creditsOffers/new")
    public String merge(CreditOffer creditOffer, Model model) {
        if(creditOffer.getId() == null) {
            storageDAO.save(creditOffer);
        } else {
            storageDAO.update(creditOffer);
        }
        return "redirect:/creditsOffers";
    }

}
