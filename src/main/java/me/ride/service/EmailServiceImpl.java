package me.ride.service;

import lombok.SneakyThrows;
import me.ride.controller.OrderRequest;
import me.ride.entity.User;
import me.ride.entity.client.Client;
import me.ride.service.context.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

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
    private SpringTemplateEngine templateEngine;

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Override
    public void sendMail(AbstractEmailContext email) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariables(email.getContext());
        String emailContent = templateEngine.process(email.getTemplateLocation(), context);

        mimeMessageHelper.setTo(email.getTo());
        mimeMessageHelper.setSubject(email.getSubject());
        mimeMessageHelper.setFrom(email.getFrom());
        mimeMessageHelper.setText(emailContent, true);
        emailSender.send(message);
    }

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
        AbstractEmailContext emailContext = null;
        switch (orderRequest.getStatus()) {
            case REFUSED:
                emailContext = new OrderRefusedEmailContext();
                break;
            case ACCEPTED:
                emailContext = new OrderAcceptedEmailContext();
                break;
            case CAR_DAMAGED:
                emailContext = new CarDamagedEmailContext();
                break;
            case UNDER_CONSIDERATION:
                emailContext = new NewOrderMailContext();
                break;
            case RETURNED:
                return;
        }
        emailContext.init(client);
        emailContext.put("orderRequest", orderRequest);
        sendMail(emailContext);
    }
}
