package com.ada.banco.application.gateways;

import com.ada.banco.domain.model.Usuario;

public interface UsuarioGateway {

    Usuario criarUsuario (Usuario usuario) throws Exception;

    Usuario buscarUsuarioPorCpf(String cpf) throws Exception;

    Usuario buscarUsuarioPorId(Long id) throws Exception;




}
