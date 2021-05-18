package me.ride.service;

import me.ride.entity.User;
import me.ride.entity.client.Client;
import org.springframework.stereotype.Service;

@Service
public interface ClientService {

    void saveClient(Client client);

    Client findClientByUser(User user);

    void updateProfile(User userForm, Client clientForm);
}
