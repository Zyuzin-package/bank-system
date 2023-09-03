package bank.system.unitTests.service;

import bank.system.model.domain.Client;
import bank.system.model.domain.Credit;
import bank.system.model.domain.CreditOffer;
import bank.system.rest.dao.repository.ClientRepository;
import bank.system.rest.dao.service.impl.ClientServiceImpl;
import bank.system.rest.exception.EntityNotFoundException;
import bank.system.rest.exception.ValidationException;
import jakarta.annotation.Resource;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@SpringBootTest
public class ClientServiceImplTest {

    @InjectMocks
    private ClientServiceImpl clientService;
    @Mock
    @Resource
    private ClientRepository clientRepository;
    private MockMvc mockMvc;
   private UUID uuid = UUID.randomUUID();
    private Client client;

    @BeforeEach
    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientRepository).build();
    }
    @Before
    public void initEntities(){
        client = Client.builder()
                .email("KorkWake@smarts.ru")
                .firstName("Kork")
                .passportID("90-77-856785")
                .phoneNumber("8-999-765-67-78")
                .secondName("Wake")
                .id(uuid)
                .build();
    }
    @Test
    public void findByIdPositive() {
        Mockito.when(clientRepository.findById(uuid)).thenReturn(Optional.ofNullable(client));

        Client foundedClient = clientService.findById(uuid);
        assertNotNull(foundedClient);
        assertEquals(foundedClient.getId(),uuid);
    }

    @Test
    public void findByIdNegative() {
        Mockito.when(clientRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> clientService.findById(uuid));
    }


    @Test
    public void updatePositive() {
        Mockito.when(clientRepository.findById(uuid)).thenReturn(Optional.of(client));
        Mockito.when(clientRepository.save(client)).thenReturn(client);

        Client savedClient = clientService.update(client);
        assertNotNull(savedClient);
        assertEquals(savedClient.getId(),uuid);
    }

    @Test
    public void updateNegative() {
        Mockito.when(clientRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> clientService.update(client));
    }

    @Test
    public void removeNegative() {
        Mockito.when(clientRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> clientService.removeById(uuid));
    }
}
