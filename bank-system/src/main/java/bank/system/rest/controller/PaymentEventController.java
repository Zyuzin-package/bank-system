package bank.system.rest.controller;

import bank.system.model.domain.PaymentEvent;
import bank.system.rest.exception.ValidationException;
import bank.system.rest.Validator;
import bank.system.rest.dao.service.impl.PaymentEventServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 *
 */
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
        validator.uuidValidator(offerId);

        List<PaymentEvent> paymentEvents = paymentEventServiceImpl.getPaymentEventByOfferId(offerId);

        model.addAttribute("paymentEvents", paymentEvents);
        return "paymentEvents";
    }

}
