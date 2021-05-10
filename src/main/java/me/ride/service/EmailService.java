package me.ride.service;

import me.ride.controller.OrderRequest;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);

    void processOrderRequest(OrderRequest orderRequest);
}
