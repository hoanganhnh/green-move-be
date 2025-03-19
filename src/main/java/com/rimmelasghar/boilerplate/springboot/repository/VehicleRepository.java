package com.rimmelasghar.boilerplate.springboot.repository;

import com.rimmelasghar.boilerplate.springboot.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    boolean existsByLicensePlate(String licensePlate);
    Optional<Vehicle> findByLicensePlate(String licensePlate);
}
