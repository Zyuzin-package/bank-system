package bank.system.rest.controller;

import bank.system.model.domain.Client;
import bank.system.model.domain.Credit;
import bank.system.model.domain.CreditOffer;
import bank.system.model.domain.PaymentEvent;
import bank.system.rest.dao.service.api.StorageDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
public class CreditOfferController {

    private final StorageDAO<CreditOffer, UUID> creditOfferDAO;
    private final StorageDAO<Credit, UUID> creditDAO;
    private final StorageDAO<Client, UUID> clientDAO;
    private final StorageDAO<PaymentEvent, UUID> paymentEventDAO;

    public CreditOfferController(StorageDAO<CreditOffer, UUID> creditOfferDAO, StorageDAO<Credit, UUID> creditDAO, StorageDAO<Client, UUID> clientDAO, StorageDAO<PaymentEvent, UUID> paymentEventDAO) {
        this.creditOfferDAO = creditOfferDAO;
        this.creditDAO = creditDAO;
        this.clientDAO = clientDAO;
        this.paymentEventDAO = paymentEventDAO;
    }

    @GetMapping("/creditsOffers")
    public String getCreditsOffers(Model model) {
        List<CreditOffer> creditOffers = creditOfferDAO.getAll();

        model.addAttribute("creditOffers", creditOffers);
        return "creditsOffers";
    }

    @GetMapping("/creditsOffers/new")
    public String getCreatePage(Model model) {
        List<Credit> creditList = creditDAO.getAll();
        List<Client> clientList = clientDAO.getAll();

        model.addAttribute("creditList", creditList);
        model.addAttribute("clientList", clientList);

        return "updateCreditOffer";
    }

    @GetMapping("/creditsOffers/remove/{id}")
    public String remove(@PathVariable String id) {

        creditOfferDAO.removeById(UUID.fromString(id));

        return "redirect:/creditsOffers";
    }

    @PostMapping("/creditsOffers/new")
    public String merge(
            @RequestParam(name = "creditId") String creditId,
            @RequestParam(name = "clientId") String clientId) {
        CreditOffer creditOffer = CreditOffer.builder()
                .credit(creditDAO.findById(UUID.fromString(creditId)))
                .client(clientDAO.findById(UUID.fromString(clientId)))
                .build();
        creditOfferDAO.save(creditOffer);

        return "redirect:/creditsOffers";
    }

}
