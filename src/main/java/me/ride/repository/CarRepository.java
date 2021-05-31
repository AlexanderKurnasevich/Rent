package me.ride.repository;

import me.ride.entity.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.lang.annotation.Native;
import java.util.Date;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    Car getCarById(Long id);

    @Query(value = "SELECT id, bags, car_type, doors, name, passengers FROM " +
            "(SELECT id, bags, car_type, doors, name, passengers FROM t_car LEFT JOIN " +
            "(SELECT car_id FROM t_car_maintenance WHERE (?1 BETWEEN first_day AND last_day OR ?2 BETWEEN first_day " +
            "AND last_day OR first_day BETWEEN ?1 AND ?2 OR last_day BETWEEN ?1 AND ?2)) AS maintenance_excl ON t_car.id = maintenance_excl.car_id " +
            "WHERE maintenance_excl.car_id IS NULL) AS c1 " +
            "JOIN " +
            "(SELECT id as c_id FROM t_car LEFT JOIN (SELECT car_id FROM t_order WHERE (?1 BETWEEN first_day AND last_day " +
            "OR ?2 BETWEEN first_day AND last_day OR first_day BETWEEN ?1 AND ?2 OR last_day BETWEEN ?1 AND ?2) AND order_status <> 'REFUSED') " +
            "AS order_day_excl ON t_car.id = order_day_excl.car_id WHERE order_day_excl.car_id IS NULL) AS c2 " +
            "ON c1.id = c2.c_id", nativeQuery = true)
        //получает 2 таблицы с автомобилями не попадающие по дате в t_order и t_car_maintenance и объединяет результат
    List<Car> findAvailableCarsBetween(Date dateFrom, Date dateTo);
}
