package com.github.dddvalueobject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ClientController {

    Logger logger = LoggerFactory.getLogger(ClientController.class);

    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @PostMapping("/client")
    public ClientDTO createClient(@RequestBody ClientDTO client) {
        logger.info("Create client request {}", client);

        var clientEntity = clientRepository.save(
                new ClientEntity(
                        UUID.randomUUID(),
                        client.demoId(),
                        client.clientNumber(), // If clientNumber and accountNumber is a String
                        client.accountNumber() // then this 2 line could be by mistake swapped.
                                               // In such case only good tests could discover mistake.
                                               // Value Object implementation will not even compile!
                ));

        return new ClientDTO(
                clientEntity.getDemoId(),
                clientEntity.getClientNumber(),  // If clientNumber and accountNumber is a String
                clientEntity.getAccountNumber()  // then this 2 line could be by mistake swapped
                                                 // In such case only good tests could discover mistake.
                                                 // Value Object implementation will not even compile!
        );
    }

    record ClientDTO(DemoId demoId, ClientNumber clientNumber, AccountNumber accountNumber) {
    }
}
