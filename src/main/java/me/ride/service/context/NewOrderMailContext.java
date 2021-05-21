package me.ride.service.context;

import me.ride.entity.client.Client;

public class NewOrderMailContext extends AbstractEmailContext{
    @Override
    public <T> void init(T context){
        Client client = (Client) context;
        put("firstName", client.getName());
        setTemplateLocation("email/new_order");
        setSubject("Заявка принята");
        setFrom("navsiak@yandex.ru");
        setTo(client.getEmail());
    }
}
