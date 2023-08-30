package bank.system.rest.controller;

import bank.system.model.domain.Client;
import bank.system.model.domain.CreditOffer;
import bank.system.model.domain.PaymentEvent;
import bank.system.rest.dao.api.service.api.StorageDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
public class PaymentEventController {

    private final StorageDAO<PaymentEvent, UUID> paymentDAO;

    private final StorageDAO<CreditOffer, UUID> creditOfferDAO;

    public PaymentEventController(StorageDAO<PaymentEvent, UUID> paymentDAO, StorageDAO<CreditOffer, UUID> creditOfferDAO) {
        this.paymentDAO = paymentDAO;
        this.creditOfferDAO = creditOfferDAO;
    }

    private String creditOfferId = "";

    @GetMapping("/paymentEvents/{offerId}")
    public String getPaymentsEvents(Model model, @PathVariable String offerId) {
        List<PaymentEvent> paymentEvents = new ArrayList<>();
        System.out.println(paymentEvents);

        for (PaymentEvent paymentEvent : paymentDAO.getAll()) {
            if (UUID.fromString(offerId).equals(paymentEvent.getCreditOffer().getId())) {
                paymentEvents.add(paymentEvent);
            }
        }
        this.creditOfferId = offerId;
        model.addAttribute("paymentEvents", paymentEvents);
        return "paymentEvents";
    }

    @GetMapping("/paymentEvents/new")
    public String getCreatePage(Model model) {
        model.addAttribute("paymentEvent", new PaymentEvent());

        return "updatePaymentEvent";
    }

    @GetMapping("/paymentEvents/edit/{id}")
    public String getUpdatePage(@PathVariable String id, Model model) {

        PaymentEvent paymentEvent = paymentDAO.findById(UUID.fromString(id));
        model.addAttribute("paymentEvent", Objects.requireNonNullElseGet(paymentEvent, Client::new));

        return "updatePaymentEvent";
    }

    @GetMapping("/paymentEvents/remove/{id}")
    public String remove(@PathVariable String id, Model model) {
        paymentDAO.removeById(UUID.fromString(id));
        return "redirect:/creditsOffers";
    }

    @PostMapping("/paymentEvents/new")
    public String merge(PaymentEvent paymentEvent, Model model) {
        if (paymentEvent.getId() == null) {
            paymentDAO.save(paymentEvent);
        } else {
            CreditOffer creditOffer = creditOfferDAO.findById(UUID.fromString(creditOfferId));
            paymentEvent.setCreditOffer(creditOffer);
            paymentDAO.update(paymentEvent);
        }
        this.creditOfferId = null;
        return "redirect:/creditsOffers";
    }

}
