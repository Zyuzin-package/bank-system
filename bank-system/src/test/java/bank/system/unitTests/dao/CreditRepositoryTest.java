package bank.system.unitTests.dao;

import bank.system.model.domain.Credit;
import bank.system.rest.dao.repository.CreditRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableJpaRepositories
@ActiveProfiles(value = "test")
public class CreditRepositoryTest {
    @Autowired
    private CreditRepository creditRepository;
    private Credit credit;

    @Before
    public void initEnvironment() {
        credit = Credit.builder()
                .interestRate(15)
                .limit(20000)
                .build();
    }


    @Test
    public void saveCreditOfferPositive() {
        Credit savedCredit = creditRepository.save(credit);
        Credit oldCredit = credit;
        oldCredit.setId(savedCredit.getId());

        assertEquals(savedCredit, oldCredit);
    }

    @Test
    @Transactional
    public void deleteCreditOfferPositive() {

        Credit savedCredit = creditRepository.save(credit);
        Credit oldCredit = credit;
        oldCredit.setId(savedCredit.getId());

        creditRepository.delete(oldCredit);
        Credit removedCredit = creditRepository.findById(savedCredit.getId()).orElse(null);

        assertNull(removedCredit);
    }


    @Test
    @Transactional
    public void updateCreditOfferPositive() {

        Credit savedOffer = creditRepository.save(credit);
        savedOffer.setInterestRate(787878);
        creditRepository.save(savedOffer);

        Credit newCredit = creditRepository.findById(savedOffer.getId()).orElse(null);

        assertEquals(newCredit, savedOffer);
    }
}
