package me.ride.service;

import lombok.SneakyThrows;
import me.ride.controller.OrderRequest;
import me.ride.entity.User;
import me.ride.entity.client.Client;
import me.ride.entity.system.Damage;
import me.ride.entity.system.Order;
import me.ride.entity.system.OrderStatus;
import me.ride.exception.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final String REFUSED_SUBJECT = "REFUSED_SUBJECT";
    private static final String REFUSED_TEXT = "REFUSED_TEXT";
    private static final String ACCEPTED_SUBJECT = "ACCEPTED_SUBJECT";
    private static final String ACCEPTED_TEXT = "ACCEPTED_TEXT";
    private static final String CAR_DAMAGED_SUBJECT = "CAR_DAMAGED_SUBJECT";
    private static final String CAR_DAMAGED_TEXT = "CAR_DAMAGED_TEXT";
    private static final String RETURNED_SUBJECT = "RETURNED_SUBJECT";
    private static final String RETURNED_TEXT = "RETURNED_TEXT";

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("navsiak@yandex.ru");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @SneakyThrows
    @Override
    public void processOrderRequest(OrderRequest orderRequest) {
        User user = orderService.show(orderRequest.getId()).getUser();
        Client client = clientService.findClientByUser(user);
        String to = client.getEmail();
        switch (orderRequest.getStatus()) {
            case REFUSED:
                sendSimpleMessage(to, REFUSED_SUBJECT, REFUSED_TEXT);
                break;
            case ACCEPTED:
                sendSimpleMessage(to, ACCEPTED_SUBJECT, ACCEPTED_TEXT);
                break;
            case CAR_DAMAGED:
                sendSimpleMessage(to, CAR_DAMAGED_SUBJECT, CAR_DAMAGED_TEXT);
                break;
            case RETURNED:
                sendSimpleMessage(to, RETURNED_SUBJECT, RETURNED_TEXT);
                break;
        }
    }
}
