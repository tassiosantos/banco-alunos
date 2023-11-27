package com.ada.banco.infrastructure.gateways;

import com.ada.banco.domain.model.Transacao;
import com.ada.banco.infrastructure.persistence.entities.TransacaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TransacaoMapper {

    void toEntity(Transacao record, @MappingTarget TransacaoEntity transacaoEntity);

    Transacao toDomain(TransacaoEntity transacaoEntity);
}
