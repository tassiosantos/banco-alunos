package com.ada.banco.infrastructure.gateways;

import com.ada.banco.infrastructure.persistence.entities.UsuarioEntity;
import com.ada.banco.domain.model.Usuario;

public class UsuarioEntityMapper {

    UsuarioEntity toEntity(Usuario usuarioDominio){
        return  new UsuarioEntity(usuarioDominio.nome(), usuarioDominio.cpf(), usuarioDominio.dataNascimento());
    }


    Usuario toDomain (UsuarioEntity usuarioEntity){
        return  new Usuario(usuarioEntity.getId(), usuarioEntity.getNome(), usuarioEntity.getCpf(), usuarioEntity.getDataNascimento() );
    }


}
