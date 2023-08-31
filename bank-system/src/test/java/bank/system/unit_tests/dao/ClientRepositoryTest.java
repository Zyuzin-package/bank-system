package bank.system.unit_tests.dao;


import bank.system.model.domain.Client;
import bank.system.rest.dao.repository.ClientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableJpaRepositories
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void saveClientPositiveTest() {
        Client client = Client.builder()
                .email("korkWake@mail.ru")
                .firstName("Kork")
                .secondName("Wake")
                .passportID("78-56")
                .phoneNumber("+89175675678")
                .build();
        Client savedClient = clientRepository.save(client);

        assertEquals(savedClient.getFirstName(), client.getFirstName());
    }

}
