package me.ride.repository;

import me.ride.entity.system.Damage;
import me.ride.entity.system.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DamageRepository extends JpaRepository<Damage, Long> {
    Damage findDamageByOrder(Order order);
}
