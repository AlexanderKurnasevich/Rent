package me.ride.service;

import me.ride.entity.User;
import me.ride.entity.client.Client;
import me.ride.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    UserService userService;

    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    public Client findClientByUser(User user) {
        return clientRepository.findClientByUserId(user.getId());
    }

    public void updateProfile(User userForm, Client clientForm) {
        User user = userService.getAuthorizedUser();
        Client client = findClientByUser(user);
        clientForm.setDateOfBirth(client.getDateOfBirth());
        clientForm.setUser(user);
        clientForm.setId(client.getId());
        clientForm.setDateOfBirth(client.getDateOfBirth());
        clientForm.setPersonalNo(client.getPersonalNo());
        clientForm.setSex(client.getSex());
        saveClient(clientForm);
    }
}
