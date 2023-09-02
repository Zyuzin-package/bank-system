package bank.system.unit_tests.dao;

import bank.system.model.domain.Client;
import bank.system.model.domain.Credit;
import bank.system.model.domain.CreditOffer;
import bank.system.model.domain.PaymentEvent;
import bank.system.rest.dao.repository.ClientRepository;
import bank.system.rest.dao.repository.CreditOfferRepository;
import bank.system.rest.dao.repository.CreditRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableJpaRepositories
public class CreditOfferRepositoryTest {
    @Autowired
    private CreditOfferRepository creditOfferRepository;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CreditRepository creditRepository;

    Credit credit;
    Client client;
    CreditOffer creditOffer;
    UUID uuid;

    @Before
    public void initEnvironment() {
        credit = Credit.builder()
                .interestRate(15)
                .limit(20000)
                .build();
        client = Client.builder()
                .email("KorkWake@smarts.ru")
                .firstName("Kork")
                .passportID("90-77-856785")
                .phoneNumber("8-999-765-67-78")
                .secondName("Wake")
                .build();
        creditOffer = CreditOffer.builder()
                .credit(credit)
                .client(client)
                .paymentSum(15000)
                .duration(12)
                .paymentEventList(List.of(new PaymentEvent()))
                .build();
    }

    @Test
    public void saveCreditOfferPositive() {

        CreditOffer savedOffer = creditOfferRepository.save(creditOffer);
        creditOffer.setId(savedOffer.getId());
        uuid = savedOffer.getId();
        assertEquals(creditOffer, savedOffer);
    }

    @Test
    public void deleteCreditOfferPositive() {

        CreditOffer savedOffer = creditOfferRepository.save(creditOffer);
        creditOffer.setId(savedOffer.getId());

        creditOfferRepository.delete(creditOffer);
        CreditOffer offer = creditOfferRepository.findById(creditOffer.getId()).orElse(null);

        assertNull(offer);
    }
}
