package com.ada.banco.application.usecase;

import com.ada.banco.application.gateways.UsuarioGateway;
import com.ada.banco.application.usecases.CadastrarUsuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ada.banco.domain.model.Usuario;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CadastrarUsuarioTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private CadastrarUsuario cadastrarUsuario;

    @Test
    public void deveCriarUsuarioComSucesso() throws Exception {
        // Given
        Usuario usuario = new Usuario(null, "Tássio", "12345678912", LocalDate.now());

        // When
        when(usuarioGateway.criarUsuario(any())).thenReturn(usuario);

        Usuario usuarioCriado = cadastrarUsuario.criarUsuario(usuario);

        // Then
        Assertions.assertNotNull(usuarioCriado, "O usuário criado não deve ser nulo");
        Assertions.assertEquals(usuario, usuarioCriado, "O usuário criado deve ser igual ao usuário fornecido");
        verify(usuarioGateway, times(1)).criarUsuario(usuario);
    }
}