package bank.system.unitTests.service;

import bank.system.model.domain.Credit;
import bank.system.rest.dao.repository.CreditRepository;
import bank.system.rest.dao.service.impl.CreditOfferServiceImpl;
import bank.system.rest.dao.service.impl.CreditServiceImpl;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.Assert.assertThrows;

@SpringBootTest
public class CreditServiceImplTest {

    @InjectMocks
    private CreditServiceImpl creditService;
    @Mock
    @Resource
    private CreditRepository creditRepository;
    @Mock
    @Resource
    private CreditOfferServiceImpl creditOfferService;
    private MockMvc mockMvc;
   private UUID uuid = UUID.randomUUID();

   private Credit credit;
    @BeforeEach
    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(creditRepository).build();
    }

    @Before
    public void initEntities(){
        credit = Credit.builder()
                .interestRate(15)
                .limit(20000)
                .id(uuid)
                .build();
    }
    @Test
    public void findByIdPositive() {
        Mockito.when(creditRepository.findById(uuid)).thenReturn(Optional.ofNullable(credit));

        Credit foundedCredit = creditService.findById(uuid);
        assertNotNull(foundedCredit);
        assertEquals(foundedCredit.getId(),uuid);
    }

    @Test
    public void findByIdNegative() {
        Mockito.when(creditRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> creditService.findById(uuid));
    }


    @Test
    public void updatePositive() {
        Mockito.when(creditRepository.findById(uuid)).thenReturn(Optional.of(credit));
        Mockito.when(creditRepository.save(credit)).thenReturn(credit);

        Credit savedCredit = creditService.update(credit);
        assertNotNull(savedCredit);
        assertEquals(savedCredit.getId(),uuid);
    }

    @Test
    public void updateNegative() {
        Mockito.when(creditRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> creditService.update(credit));
    }

    @Test
    public void removeNegative() {
        Mockito.when(creditRepository.findById(uuid)).thenReturn(null);
        Mockito.when(creditOfferService.findByCredit(uuid)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class,
                () -> creditService.removeById(uuid));
    }
}

