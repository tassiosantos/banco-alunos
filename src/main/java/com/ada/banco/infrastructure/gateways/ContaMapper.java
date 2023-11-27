package com.ada.banco.infrastructure.gateways;

import com.ada.banco.domain.model.Conta;
import com.ada.banco.infrastructure.persistence.entities.ContaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContaMapper {

    void toEntity(Conta conta, @MappingTarget ContaEntity contaEntity);

    Conta toDomain(ContaEntity contaEntity);

}
