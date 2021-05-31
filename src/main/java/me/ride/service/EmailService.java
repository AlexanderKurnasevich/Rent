package me.ride.service;

import me.ride.entity.system.OrderRequest;
import me.ride.service.context.AbstractEmailContext;

import javax.mail.MessagingException;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);

    void processOrderRequest(OrderRequest orderRequest);

    void sendMail(AbstractEmailContext email) throws MessagingException;
}
