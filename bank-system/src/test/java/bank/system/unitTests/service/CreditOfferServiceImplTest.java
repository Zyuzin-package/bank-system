package bank.system.unitTests.service;

import bank.system.model.domain.Client;
import bank.system.model.domain.Credit;
import bank.system.model.domain.CreditOffer;
import bank.system.rest.dao.repository.CreditOfferRepository;
import bank.system.rest.dao.service.impl.CreditOfferServiceImpl;
import bank.system.rest.exception.EntityNotFoundException;
import jakarta.annotation.Resource;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.Assert.assertThrows;

@SpringBootTest
@ActiveProfiles(value = "test")
public class CreditOfferServiceImplTest {
    @InjectMocks
    private CreditOfferServiceImpl creditOfferService;
    @Mock
    @Resource
    private CreditOfferRepository creditOfferRepository;
    private MockMvc mockMvc;
    private final UUID uuidClient = UUID.randomUUID();
    private final UUID uuidCredit = UUID.randomUUID();
    private final UUID uuidCreditOffer = UUID.randomUUID();
    private Credit credit;
    private Client client;
    private CreditOffer creditOffer;

    @BeforeEach
    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(creditOfferRepository).build();
    }

    @Before
    public void initEntities() {
        credit = Credit.builder()
                .interestRate(15)
                .limit(20000)
                .id(uuidCredit)
                .build();
        client = Client.builder()
                .email("KorkWake@smarts.ru")
                .firstName("Kork")
                .passportID("90-77-856785")
                .phoneNumber("8-999-765-67-78")
                .secondName("Wake")
                .id(uuidClient)
                .build();
        creditOffer = CreditOffer.builder()
                .credit(credit)
                .client(client)
                .paymentSum(15000)
                .duration(12)
                .paymentEventList(new ArrayList<>())
                .id(uuidCreditOffer)
                .build();
    }

    @Test
    public void findByIdPositive() {
        Mockito.when(creditOfferRepository.findById(uuidCreditOffer)).thenReturn(Optional.ofNullable(creditOffer));

        CreditOffer foundedClientOffer = creditOfferService.findById(uuidCreditOffer);
        assertNotNull(foundedClientOffer);
        assertEquals(foundedClientOffer.getId(), uuidCreditOffer);
    }

    @Test
    public void findByIdNegative() {
        Mockito.when(creditOfferRepository.findById(uuidCreditOffer)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> creditOfferService.findById(uuidCreditOffer));
    }


    @Test
    public void updatePositive() {
        Mockito.when(creditOfferRepository.findById(uuidCreditOffer)).thenReturn(Optional.of(creditOffer));
        Mockito.when(creditOfferRepository.save(creditOffer)).thenReturn(creditOffer);

        CreditOffer savedCreditOffer = creditOfferService.update(creditOffer);
        assertNotNull(savedCreditOffer);
        assertEquals(savedCreditOffer.getId(), uuidCreditOffer);
    }

    @Test
    public void updateNegative() {
        Mockito.when(creditOfferRepository.findById(uuidCreditOffer)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> creditOfferService.update(creditOffer));
    }

    @Test
    public void removeNegative() {
        Mockito.when(creditOfferRepository.findById(uuidCreditOffer)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> creditOfferService.removeById(uuidCreditOffer));
    }
}

