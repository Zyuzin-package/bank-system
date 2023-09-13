package bank.system.rest.controller;

import bank.system.model.domain.PaymentEvent;
import bank.system.model.entity_treatment.validators.UUIDValidatorImpl;
import bank.system.rest.dao.service.impl.CreditOfferServiceImpl;
import bank.system.rest.dao.service.impl.PaymentEventServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

/**
 *
 */
@Controller
public class PaymentEventController {

    private final PaymentEventServiceImpl paymentEventServiceImpl;
    private final CreditOfferServiceImpl creditOfferService;
    private final UUIDValidatorImpl uuidValidatorImpl;


    public PaymentEventController(PaymentEventServiceImpl paymentEventServiceImpl, CreditOfferServiceImpl creditOfferService, UUIDValidatorImpl uuidValidatorImpl) {
        this.paymentEventServiceImpl = paymentEventServiceImpl;
        this.creditOfferService = creditOfferService;
        this.uuidValidatorImpl = uuidValidatorImpl;
    }

    @GetMapping("/paymentEvents/{offerId}")
    public String getPaymentsEvents(Model model, @PathVariable String offerId) {
        uuidValidatorImpl.validate(offerId);

        List<PaymentEvent> paymentEvents = paymentEventServiceImpl.getPaymentEventByOfferId(offerId);

        model.addAttribute("paymentEvents", paymentEvents);
        model.addAttribute("finalSumByCredit", creditOfferService.finalSumByCredit(creditOfferService.findById(UUID.fromString(offerId))));
        return "paymentEvents";
    }

}
