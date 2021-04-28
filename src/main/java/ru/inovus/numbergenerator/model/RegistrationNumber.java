package ru.inovus.numbergenerator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name = "RegistrationNumber")
@Table(name = "registration_number",
        uniqueConstraints = @UniqueConstraint(name = "reg_plate_unique", columnNames = "registration_plate"))
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class RegistrationNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "registration_plate", nullable = false, updatable = false)
    private String registrationPlate;

    @Column(name = "region", nullable = false, updatable = false)
    private Integer region;

    @Column(name = "country", nullable = false, updatable = false)
    private Country country;
}
