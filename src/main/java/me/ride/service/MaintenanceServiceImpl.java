package me.ride.service;

import me.ride.entity.system.Maintenance;
import me.ride.repository.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    public boolean isCarFreeByMaintenance(Long carId, Date dateFrom, Date dateTo) {
        return maintenanceRepository.findMaintenanceByByCarBetween(carId, dateFrom, dateTo).isEmpty();
    }

    public Maintenance findMaintenanceById(Long id) {
        return maintenanceRepository.findMaintenanceById(id);
    }

    public void save(Maintenance maintenance) {
        maintenanceRepository.save(maintenance);
    }

    public List<Maintenance> getMaintenanceList() {
        return maintenanceRepository.findAll();
    }
}
