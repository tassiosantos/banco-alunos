package com.ada.banco.infrastructure.gateways;

import com.ada.banco.domain.model.Usuario;
import com.ada.banco.infrastructure.persistence.entities.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    void toEntity(Usuario usuario, @MappingTarget UsuarioEntity usuarioEntity);


    Usuario toModel(UsuarioEntity usuarioEntity);

}
