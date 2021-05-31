package me.ride.service;

import me.ride.entity.User;
import me.ride.entity.client.Client;

public interface ClientService {

    void saveClient(Client client);

    Client findClientByUser(User user);

    void updateProfile(User userForm, Client clientForm);
}
