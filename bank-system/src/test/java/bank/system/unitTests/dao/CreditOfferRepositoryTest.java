package bank.system.unitTests.dao;

import bank.system.model.domain.Client;
import bank.system.model.domain.Credit;
import bank.system.model.domain.CreditOffer;
import bank.system.rest.dao.repository.CreditOfferRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableJpaRepositories
@ActiveProfiles(value = "test")
public class CreditOfferRepositoryTest {
    @Autowired
    private CreditOfferRepository creditOfferRepository;
    private CreditOffer creditOffer;

    @Before
    public void initEnvironment() {
        Credit credit = Credit.builder()
                .interestRate(15)
                .limit(20000)
                .build();
        Client client = Client.builder()
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
                .paymentEventList(new ArrayList<>())
                .build();
    }


    @Test
    public void saveCreditOfferPositive() {
        CreditOffer savedOffer = creditOfferRepository.save(creditOffer);
        CreditOffer oldOffer = creditOffer;

        oldOffer.setId(savedOffer.getId());
        assertEquals(oldOffer, savedOffer);
    }

    @Test
    @Transactional
    public void deleteCreditOfferPositive() {

        CreditOffer savedOffer = creditOfferRepository.save(creditOffer);
        creditOffer.setId(savedOffer.getId());

        creditOfferRepository.delete(creditOffer);
        CreditOffer offer = creditOfferRepository.findById(creditOffer.getId()).orElse(null);

        assertNull(offer);
    }


    @Test
    @Transactional
    public void updateCreditOfferPositive() {

        CreditOffer savedOffer = creditOfferRepository.save(creditOffer);
        savedOffer.setDuration(56);
        creditOfferRepository.save(savedOffer);

        CreditOffer newOffer = creditOfferRepository.findById(creditOffer.getId()).orElse(null);

        assertEquals(newOffer, savedOffer);
    }

}
