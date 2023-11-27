package com.ada.banco.application.usecases;

import com.ada.banco.application.gateways.UsuarioGateway;
import com.ada.banco.domain.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class CadastrarUsuario {

    private UsuarioGateway usuarioGateway;

    public CadastrarUsuario(UsuarioGateway usuarioGateway){
        this.usuarioGateway = usuarioGateway;
    }
    public Usuario criarUsuario(Usuario usuario) throws Exception {
        return  usuarioGateway.criarUsuario(usuario);
    }
}
