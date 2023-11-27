package com.ada.banco.infrastructure.gateways;

import com.ada.banco.application.gateways.UsuarioGateway;
import com.ada.banco.domain.model.Usuario;
import com.ada.banco.infrastructure.persistence.entities.UsuarioEntity;
import com.ada.banco.infrastructure.persistence.repositories.UsuarioRepository;

import java.util.Optional;

public class UsuarioGatewayImpl implements UsuarioGateway {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioEntityMapper usuarioEntityMapper;

    public UsuarioGatewayImpl(UsuarioRepository usuarioRepository, UsuarioEntityMapper usuarioEntityMapper){
        this.usuarioRepository = usuarioRepository;
        this.usuarioEntityMapper = usuarioEntityMapper;
    }
    @Override
    public Usuario criarUsuario(Usuario usuario) throws Exception {
        if(usuarioRepository.findById(usuario.id()).isPresent()){
            throw new Exception("Usuario com esse cpf já existe");
        }
        return usuarioEntityMapper.toDomain(usuarioRepository.save(usuarioEntityMapper.toEntity(usuario)));
    }

    @Override
    public Usuario buscarUsuarioPorCpf(String cpf) throws Exception {
        Optional<UsuarioEntity> usuarioEntity = Optional.ofNullable(usuarioRepository.findByCpf(cpf)).orElseThrow(
                () -> new Exception("Usuário com esse cpf não existe.")
        );
        return usuarioEntityMapper.toDomain(usuarioEntity.get());
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id) throws Exception {
        return usuarioRepository.findById(id)
                .map(usuarioEntityMapper::toDomain)
                .orElseThrow(() -> new Exception("Usuário não existe."));
    }


}
