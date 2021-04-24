package me.ride.service;

import me.ride.entity.client.Client;
import me.ride.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public void saveClient(Client client) {
        clientRepository.save(client);
    }

}
