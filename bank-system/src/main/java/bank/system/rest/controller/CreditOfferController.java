package bank.system.rest.controller;

import bank.system.model.domain.Client;
import bank.system.model.domain.Credit;
import bank.system.model.domain.CreditOffer;
import bank.system.model.entity_treatment.validators.CreditOfferValidatorImpl;
import bank.system.model.entity_treatment.validators.UUIDValidatorImpl;
import bank.system.rest.exception.ServerException;
import bank.system.rest.dao.service.impl.ClientServiceImpl;
import bank.system.rest.dao.service.impl.CreditOfferServiceImpl;
import bank.system.rest.dao.service.impl.CreditServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class CreditOfferController {

    private final CreditOfferServiceImpl creditOfferServiceImpl;
    private final CreditServiceImpl creditServiceImpl;
    private final ClientServiceImpl clientServiceImpl;

    private final CreditOfferValidatorImpl creditOfferValidation;

    private final UUIDValidatorImpl uuidValidatorImpl;

    public CreditOfferController(CreditOfferServiceImpl creditOfferServiceImpl, CreditServiceImpl creditServiceImpl, ClientServiceImpl clientServiceImpl, CreditOfferValidatorImpl creditOfferValidation, UUIDValidatorImpl uuidValidatorImpl) {
        this.creditOfferServiceImpl = creditOfferServiceImpl;
        this.creditServiceImpl = creditServiceImpl;
        this.clientServiceImpl = clientServiceImpl;
        this.creditOfferValidation = creditOfferValidation;
        this.uuidValidatorImpl = uuidValidatorImpl;
    }

    @GetMapping("/creditsOffers")
    public String getCreditsOffers(Model model) {
        List<CreditOffer> creditOffers = creditOfferServiceImpl.getAll();

        model.addAttribute("creditOffers", creditOffers);
        return "creditsOffers";
    }

    @GetMapping("/creditsOffers/new")
    public String getCreatePage(Model model) {
        List<Credit> creditList = creditServiceImpl.getAll();
        List<Client> clientList = clientServiceImpl.getAll();

        model.addAttribute("creditList", creditList);
        model.addAttribute("clientList", clientList);

        return "updateCreditOffer";
    }

    @GetMapping("/creditsOffers/remove/{id}")
    public String remove(@PathVariable String id) {
        uuidValidatorImpl.validate(id);

        creditOfferServiceImpl.removeById(UUID.fromString(id));
        return "redirect:/creditsOffers";
    }

    @PostMapping("/creditsOffers/new")
    public String merge(
            @RequestParam(name = "creditId") String creditId,
            @RequestParam(name = "clientId") String clientId,
            @RequestParam(name = "duration") Integer duration,
            @RequestParam(name = "fullsum") String fullsum
    ) throws ServerException {
        uuidValidatorImpl.validate(creditId);
        uuidValidatorImpl.validate(clientId);

        Credit credit = creditServiceImpl.findById(UUID.fromString(creditId));

        CreditOffer creditOffer = CreditOffer.builder()
                .credit(credit)
                .client(clientServiceImpl.findById(UUID.fromString(clientId)))
                .duration(duration)
                .paymentSum(Double.parseDouble(fullsum))
                .build();

        creditOfferValidation.validate(creditOffer);
        creditOfferServiceImpl.save(creditOffer);


        return "redirect:/creditsOffers";
    }

}
