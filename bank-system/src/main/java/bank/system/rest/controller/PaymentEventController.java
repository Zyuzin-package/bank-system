package bank.system.rest.controller;

import bank.system.model.domain.Client;
import bank.system.model.domain.CreditOffer;
import bank.system.model.domain.PaymentEvent;
import bank.system.model.exception.ValidationException;
import bank.system.rest.Validator;
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
    private final Validator validator;


    public PaymentEventController(PaymentEventServiceImpl paymentEventServiceImpl, Validator validator) {
        this.paymentEventServiceImpl = paymentEventServiceImpl;
        this.validator = validator;
    }

    @GetMapping("/paymentEvents/{offerId}")
    public String getPaymentsEvents(Model model, @PathVariable String offerId) {
        String validErrors = validator.uuidValidator(offerId);
        if (validErrors != null) {
            throw new ValidationException(validErrors);
        }

        List<PaymentEvent> paymentEvents = paymentEventServiceImpl.getPaymentEventByOfferId(offerId);

        model.addAttribute("paymentEvents", paymentEvents);
        return "paymentEvents";
    }

}
