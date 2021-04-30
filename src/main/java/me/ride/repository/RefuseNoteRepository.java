package me.ride.repository;

import me.ride.entity.system.Order;
import me.ride.entity.system.RefuseNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefuseNoteRepository extends JpaRepository<RefuseNote, Long> {
}
