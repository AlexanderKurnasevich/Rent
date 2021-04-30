package me.ride.service;

import me.ride.entity.system.Order;
import me.ride.repository.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    public boolean isCarFreeFindByOrder(Order order){
        return maintenanceRepository.findMaintenanceByByCarBetween(order.getCar().getId(), order.getFirstDay(), order.getLastDay()).size() == 0;
    }
}
