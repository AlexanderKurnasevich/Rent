package me.ride.service;

import me.ride.entity.system.Maintenance;

import java.util.Date;
import java.util.List;

public interface MaintenanceService {

    boolean isCarFreeByMaintenance(Long carId, Date dateFrom, Date dateTo);

    Maintenance findMaintenanceById(Long id);

    void save(Maintenance maintenance);

    List<Maintenance> getMaintenanceList();
}
