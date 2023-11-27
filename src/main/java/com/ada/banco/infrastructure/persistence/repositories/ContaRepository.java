package com.ada.banco.infrastructure.persistence.repositories;

import com.ada.banco.infrastructure.persistence.entities.ContaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContaRepository extends JpaRepository<ContaEntity, Long> {


    Optional<ContaEntity> findByNumeroConta(String numeroConta);

    Optional<ContaEntity> findByTitular(Long titular);
}
