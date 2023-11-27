package com.ada.banco.infrastructure.gateways;

import com.ada.banco.domain.model.Usuario;
import com.ada.banco.infrastructure.persistence.entities.UsuarioEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ada.banco.infrastructure.persistence.repositories.UsuarioRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioGatewayImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioEntityMapper usuarioEntityMapper;

    @InjectMocks
    private UsuarioGatewayImpl usuarioGatewayImpl;

    @Test
    public void deveCriarUsuarioComSucesso() throws Exception {
//        Usuario usuarioNovo = new Usuario();
        Usuario usuario = new Usuario(1L, "tassio", "12312312321", LocalDate.now());
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        when(usuarioRepository.findById(usuario.id())).thenReturn(Optional.empty());
        when(usuarioEntityMapper.toEntity(usuario)).thenReturn(usuarioEntity);
        when(usuarioRepository.save(usuarioEntity)).thenReturn(usuarioEntity);
        when(usuarioEntityMapper.toDomain(usuarioEntity)).thenReturn(usuario);

        Usuario resultado = usuarioGatewayImpl.criarUsuario(usuario);

        assertNotNull(resultado);
        assertEquals(usuario, resultado);
    }

    @Test
    public void deveLancarExcecaoQuandoUsuarioJaExiste() {
        Usuario usuario = new Usuario(1L, "tassio", "12312312321", LocalDate.now());
        when(usuarioRepository.findById(usuario.id())).thenReturn(Optional.of(new UsuarioEntity()));

        Exception excecao = assertThrows(Exception.class, () -> usuarioGatewayImpl.criarUsuario(usuario));

        assertEquals("Usuario com esse cpf já existe", excecao.getMessage());
    }


    @Test
    public void deveBuscarUsuarioPorCpfComSucesso() throws Exception {
        String cpf = "12345678900";
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        Usuario usuario = new Usuario(1L, "tassio", "12312312321", LocalDate.now());

        when(usuarioRepository.findByCpf(cpf)).thenReturn(Optional.of(usuarioEntity));
        when(usuarioEntityMapper.toDomain(usuarioEntity)).thenReturn(usuario);

        Usuario resultado = usuarioGatewayImpl.buscarUsuarioPorCpf(cpf);

        assertNotNull(resultado);
        assertEquals(usuario, resultado);
    }

    @Test
    public void deveLancarExcecaoQuandoUsuarioCpfNaoEncontrado() {
        String cpf = "12345678900";

        when(usuarioRepository.findByCpf(cpf)).thenReturn(null);

        Exception excecao = assertThrows(Exception.class, () -> usuarioGatewayImpl.buscarUsuarioPorCpf(cpf));

        assertEquals("Usuário com esse cpf não existe.", excecao.getMessage());
    }

    @Test
    public void deveBuscarUsuarioPorIdComSucesso() throws Exception {
        Long id = 1L;
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        Usuario usuario = new Usuario(1L, "tassio", "12312312321", LocalDate.now());

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioEntity));
        when(usuarioEntityMapper.toDomain(usuarioEntity)).thenReturn(usuario);

        Usuario resultado = usuarioGatewayImpl.buscarUsuarioPorId(id);

        assertNotNull(resultado);
        assertEquals(usuario, resultado);
    }

    @Test
    public void deveLancarExcecaoQuandoUsuarioIdNaoEncontrado() {
        Long id = 1L;

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        Exception excecao = assertThrows(Exception.class, () -> usuarioGatewayImpl.buscarUsuarioPorId(id));

        assertEquals("Usuário não existe.", excecao.getMessage());
    }


}