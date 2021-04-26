package ru.inovus.numbergenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.inovus.numbergenerator.model.RegistrationNumber;

import java.util.Optional;

public interface RegistrationNumberRepository extends JpaRepository<RegistrationNumber, Long> {
    Optional<RegistrationNumber> findFirstByOrderByIdDesc();
}
