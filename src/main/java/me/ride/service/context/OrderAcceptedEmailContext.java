package me.ride.service.context;

import me.ride.entity.client.Client;

public class OrderAcceptedEmailContext extends AbstractEmailContext {

    @Override
    public <T> void init(T context) {
        Client client = (Client) context;
        put("firstName", client.getName());
        setTemplateLocation("email/accepted");
        setSubject("Заявка удовлетварена");
        setFrom("navsiak@yandex.ru");
        setTo(client.getEmail());
    }
}
