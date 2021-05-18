package me.ride.service;

import me.ride.entity.car.Car;
import me.ride.entity.system.RentPrice;
import me.ride.repository.RentPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    private RentPriceRepository rentPriceRepository;

    public void save(RentPrice rentPrice) {
        rentPriceRepository.save(rentPrice);
    }

    public Double getPrice(Car car, Date firstDay, Date lastDay) {
        Double res = rentPriceRepository.getRentPriceByCar(car).getPricePerDay();
        Long days = 1 + TimeUnit.DAYS.convert(Math.abs(firstDay.getTime() - lastDay.getTime()), TimeUnit.MILLISECONDS);
        return res * days;
    }

    public RentPrice getRentPriceByCar(Car car) {
        return rentPriceRepository.getRentPriceByCar(car);
    }
}
