package me.ride.repository;

import me.ride.entity.system.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

interface MaintenanceRepository  extends JpaRepository<Maintenance, Long> {
}
