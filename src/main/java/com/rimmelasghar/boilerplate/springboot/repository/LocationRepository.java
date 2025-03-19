package com.rimmelasghar.boilerplate.springboot.repository;

import com.rimmelasghar.boilerplate.springboot.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    boolean existsByNameAndAddress(String name, String address);
}
