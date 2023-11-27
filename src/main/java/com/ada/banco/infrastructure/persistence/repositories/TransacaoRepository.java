package com.ada.banco.infrastructure.persistence.repositories;

import com.ada.banco.domain.model.Transacao;
import com.ada.banco.infrastructure.persistence.entities.TransacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<TransacaoEntity, Long> {
}
