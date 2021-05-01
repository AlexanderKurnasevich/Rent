package me.ride.repository;

import me.ride.entity.system.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface MaintenanceRepository  extends JpaRepository<Maintenance, Long> {

    @Query(value = "select m from Maintenance m where m.car.id=?1 AND ( ?2 between m.firstDay and m.lastDay or ?3 between m.firstDay and m.lastDay " +
            "or m.firstDay between ?2 and ?3 or m.lastDay between ?2 and ?3)")
    List<Maintenance> findMaintenanceByByCarBetween(Long carId, Date dateFrom, Date dateTo);
}
