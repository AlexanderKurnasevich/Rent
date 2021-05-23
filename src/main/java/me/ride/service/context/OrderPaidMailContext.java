package me.ride.service.context;

import me.ride.entity.client.Client;

public class OrderPaidMailContext extends AbstractEmailContext {
    @Override
    public <T> void init(T context){
        Client client = (Client) context;
        put("firstName", client.getName());
        setTemplateLocation("email/paid");
        setSubject("Подтверждение оплаты");
        setFrom("navsiak@yandex.ru");
        setTo(client.getEmail());
    }
}
