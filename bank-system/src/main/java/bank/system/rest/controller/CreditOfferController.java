package bank.system.rest.controller;

import bank.system.model.domain.Client;
import bank.system.model.domain.Credit;
import bank.system.model.domain.CreditOffer;
import bank.system.model.domain.PaymentEvent;
import bank.system.rest.dao.service.api.StorageDAO;
import bank.system.rest.dao.service.impl.ClientServiceImpl;
import bank.system.rest.dao.service.impl.CreditOfferServiceImpl;
import bank.system.rest.dao.service.impl.CreditServiceImpl;
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

    private final CreditOfferServiceImpl creditOfferServiceImpl;
    private final CreditServiceImpl creditServiceImpl;
    private final ClientServiceImpl clientServiceImpl;


    public CreditOfferController(CreditOfferServiceImpl creditOfferServiceImpl, CreditServiceImpl creditServiceImpl, ClientServiceImpl clientServiceImpl) {
        this.creditOfferServiceImpl = creditOfferServiceImpl;
        this.creditServiceImpl = creditServiceImpl;
        this.clientServiceImpl = clientServiceImpl;
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
        creditOfferServiceImpl.removeById(UUID.fromString(id));
        return "redirect:/creditsOffers";
    }

    @PostMapping("/creditsOffers/new")
    public String merge(
            @RequestParam(name = "creditId") String creditId,
            @RequestParam(name = "clientId") String clientId,
            @RequestParam(name = "duration") Integer duration,
            @RequestParam(name = "fullsum") String fullsum
    ) {
        Credit credit = creditServiceImpl.findById(UUID.fromString(creditId));

        if(Double.parseDouble(fullsum) > credit.getLimit()){

        }

        CreditOffer creditOffer = CreditOffer.builder()
                .credit(credit)
                .client(clientServiceImpl.findById(UUID.fromString(clientId)))
                .duration(duration)
                .paymentSum(Double.parseDouble(fullsum))
                .build();
        creditOfferServiceImpl.save(creditOffer);


        return "redirect:/creditsOffers";
    }

}
