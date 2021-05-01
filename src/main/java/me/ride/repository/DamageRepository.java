package me.ride.repository;

import me.ride.entity.system.Damage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DamageRepository extends JpaRepository<Damage, Long> {
}
