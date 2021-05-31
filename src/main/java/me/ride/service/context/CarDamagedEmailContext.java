package me.ride.service.context;

import me.ride.entity.client.Client;

public class CarDamagedEmailContext extends AbstractEmailContext {
    @Override
    public <T> void init(T context) {
        Client client = (Client) context;
        put("firstName", client.getName());
        setTemplateLocation("email/car_damaged");
        setSubject("Машина возвращена с повреждениями");
        setFrom("navsiak@yandex.ru");
        setTo(client.getEmail());
    }
}
