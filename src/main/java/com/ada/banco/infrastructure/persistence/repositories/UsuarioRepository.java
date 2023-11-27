package com.ada.banco.infrastructure.persistence.repositories;

import com.ada.banco.infrastructure.persistence.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {


    Optional<UsuarioEntity> findByCpf(String cpf);
}
