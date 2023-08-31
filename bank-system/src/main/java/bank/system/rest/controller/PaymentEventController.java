package bank.system.rest.controller;

import bank.system.model.domain.Client;
import bank.system.model.domain.CreditOffer;
import bank.system.model.domain.PaymentEvent;
import bank.system.rest.dao.service.api.StorageDAO;
import bank.system.rest.dao.service.impl.CreditOfferServiceImpl;
import bank.system.rest.dao.service.impl.PaymentEventServiceImpl;
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

    private final PaymentEventServiceImpl paymentEventServiceImpl;

    private final CreditOfferServiceImpl creditOfferServiceImpl;

    public PaymentEventController(PaymentEventServiceImpl paymentEventServiceImpl, CreditOfferServiceImpl creditOfferServiceImpl) {
        this.paymentEventServiceImpl = paymentEventServiceImpl;
        this.creditOfferServiceImpl = creditOfferServiceImpl;
    }

    private String creditOfferId = "";

    @GetMapping("/paymentEvents/{offerId}")
    public String getPaymentsEvents(Model model, @PathVariable String offerId) {
        List<PaymentEvent> paymentEvents = paymentEventServiceImpl.getPaymentEventByOfferId(offerId);

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

        PaymentEvent paymentEvent = paymentEventServiceImpl.findById(UUID.fromString(id));
        model.addAttribute("paymentEvent", Objects.requireNonNullElseGet(paymentEvent, Client::new));

        return "updatePaymentEvent";
    }

    @GetMapping("/paymentEvents/remove/{id}")
    public String remove(@PathVariable String id) {
        paymentEventServiceImpl.removeById(UUID.fromString(id));
        return "redirect:/creditsOffers";
    }

    @PostMapping("/paymentEvents/new")
    public String merge(PaymentEvent paymentEvent) {
        CreditOffer creditOffer = creditOfferServiceImpl.findById(UUID.fromString(creditOfferId));
        paymentEvent.setCreditOffer(creditOffer);

        if (paymentEvent.getId() == null) {
            paymentEventServiceImpl.save(paymentEvent);
        } else {
            paymentEventServiceImpl.update(paymentEvent);
        }

        this.creditOfferId = null;
        return "redirect:/creditsOffers";
    }

}
