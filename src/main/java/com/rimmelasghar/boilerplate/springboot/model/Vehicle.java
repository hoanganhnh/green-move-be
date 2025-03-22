package com.rimmelasghar.boilerplate.springboot.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "VEHICLES")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String type;

    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;

    @Column(nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "price_per_day", nullable = true)
    private BigDecimal pricePerDay;
    
    @Column(name = "price_per_month", nullable = true)
    private BigDecimal pricePerMonth;
    
    @Column(name = "price_per_year", nullable = true)
    private BigDecimal pricePerYear;

    @Column(nullable = false)
    private String image;
}
