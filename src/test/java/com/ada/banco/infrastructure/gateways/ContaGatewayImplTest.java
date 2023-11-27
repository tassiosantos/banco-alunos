package com.ada.banco.infrastructure.gateways;

import com.ada.banco.domain.model.Conta;
import com.ada.banco.domain.model.Usuario;
import com.ada.banco.infrastructure.persistence.entities.ContaEntity;
import com.ada.banco.infrastructure.persistence.entities.UsuarioEntity;
import com.ada.banco.infrastructure.persistence.repositories.ContaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ContaGatewayImplTest {

    @Mock
    private ContaMapper contaMapper;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private UsuarioGatewayImpl usuarioGateway;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private ContaGatewayImpl contaGatewayImpl;

    @Test
    public void deveCriarContaComSucesso() throws Exception {
        // Dados de entrada
        Conta conta = new Conta(null, "123-x", "112", "corrente", BigDecimal.valueOf(1000), 1L);
        Usuario usuario = new Usuario(1L, "tassio", "12312312321", LocalDate.now());
        UsuarioEntity usuarioEntity = new UsuarioEntity("tassio","12312312321",LocalDate.now());
        ContaEntity contaEntity = new ContaEntity();
        Conta contaCriada = new Conta(1L, "123-x", "112", "corrente", BigDecimal.valueOf(1000), 1L);
        ContaEntity contaEntityCriada = new ContaEntity(1L, "123-x", "112", "corrente", BigDecimal.valueOf(1000), usuarioEntity);

        when(contaRepository.findByNumeroConta(conta.numeroConta())).thenReturn(Optional.empty());
        when(usuarioGateway.buscarUsuarioPorId(conta.titular())).thenReturn(usuario);
        when(contaRepository.findByTitular(conta.titular())).thenReturn(Optional.empty());
        doAnswer(invocation -> {
            Conta contaArg = invocation.getArgument(0);
            ContaEntity contaEntityArg = invocation.getArgument(1);
            return null;
        }).when(contaMapper).toEntity(any(Conta.class), any(ContaEntity.class));
        when(contaRepository.save(any(ContaEntity.class))).thenReturn(contaEntityCriada);
        when(contaMapper.toDomain(any(ContaEntity.class))).thenReturn(contaCriada);

        // Execução
        Conta resultado = contaGatewayImpl.criarConta(conta);

        // Verificações
        assertNotNull(resultado);
        assertEquals(contaCriada, resultado);
    }

    @Test
    public void deveLancarExcecaoQuandoTitularNuloAoCriarConta() {

        Conta conta = new Conta(null, "123-x", "112", "conta corrente", BigDecimal.valueOf(1000), null);

        Exception excecao = assertThrows(Exception.class, () -> contaGatewayImpl.criarConta(conta));
        assertEquals("Não é possível salvar conta sem titular", excecao.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoContaSemNumero() {
        Conta conta = new Conta(null, "123-x", null, "conta corrente", BigDecimal.valueOf(1000), 1L);

        // Execução e Verificação
        Exception excecao = assertThrows(Exception.class, () -> contaGatewayImpl.criarConta(conta));
        assertEquals("Não é possível criar conta sem um número", excecao.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoContaComIdRegistrado() {
        // Dados de entrada
        Conta conta = new Conta(1L, "123-x", "a123", "conta corrente", BigDecimal.valueOf(1000), 1L);

        // Execução e Verificação
        Exception excecao = assertThrows(Exception.class, () -> contaGatewayImpl.criarConta(conta));
        assertEquals("Não é possível criar conta com id registrado, apenas atualizar", excecao.getMessage());
    }

    @Test
    public void deveLancarExcecaoParaTipoDeContaInvalido() {
        // Dados de entrada
        Conta conta = new Conta(null, "123-x", "a123", null, BigDecimal.valueOf(1000), 1L);

        // Execução e Verificação
        Exception excecao = assertThrows(Exception.class, () -> contaGatewayImpl.criarConta(conta));
        assertEquals("Tipo da conta inválido ou em branco.", excecao.getMessage());
    }

    @Test
    public void deveLancarExcecaoParaNumeroDeContaExistente() {
        // Dados de entrada
        Conta conta = new Conta(null, "123-x", "a123", "corrente", BigDecimal.valueOf(1000), 1L);

        when(contaRepository.findByNumeroConta(conta.numeroConta())).thenReturn(Optional.of(new ContaEntity()));

        // Execução e Verificação
        Exception excecao = assertThrows(Exception.class, () -> contaGatewayImpl.criarConta(conta));
        assertEquals("Não é possível criar duas contas com mesmo número", excecao.getMessage());
    }

    @Test
    public void deveLancarExcecaoParaTitularInvalido() throws Exception {
        // Dados de entrada
        Conta conta = new Conta(null, "123-x", "a123", "corrente", BigDecimal.valueOf(1000), 100L);
        when(usuarioGateway.buscarUsuarioPorId(conta.titular())).thenReturn(null);

        // Execução e Verificação
        Exception excecao = assertThrows(Exception.class, () -> contaGatewayImpl.criarConta(conta));
        assertEquals("Titular dessa conta é inválido", excecao.getMessage());
    }

    @Test
    public void deveLancarExcecaoParaTitularComContaRegistrada() throws Exception {
        // Dados de entrada
        Conta conta = new Conta(null, "123-x", "a123", "corrente", BigDecimal.valueOf(1000), 1L);
        Usuario usuarioComConta = new Usuario(1L, "tassio", "12312312312", LocalDate.now());

        when(usuarioGateway.buscarUsuarioPorId(conta.titular())).thenReturn(usuarioComConta);
        when(contaRepository.findByTitular(conta.titular())).thenReturn(Optional.of(new ContaEntity()));

        // Execução e Verificação
        Exception excecao = assertThrows(Exception.class, () -> contaGatewayImpl.criarConta(conta));
        assertEquals("Titular já possui uma conta registrada.", excecao.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoAtualizarContaSemTitular() {
        Conta conta = new Conta(1L, "123-x", "a123", "corrente", BigDecimal.valueOf(1000), null);

        Exception excecao = assertThrows(Exception.class, () -> contaGatewayImpl.atualizarConta(conta));

        assertEquals("Não é possível salvar conta sem titular", excecao.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoAtualizarContaSemNumero() {
        Conta conta = new Conta(null, "123-x", null, "corrente", BigDecimal.valueOf(1000), 1L);

        Exception excecao = assertThrows(Exception.class, () -> contaGatewayImpl.atualizarConta(conta));

        assertEquals("Não é possível atualizar conta sem um número", excecao.getMessage());
    }


    @Test
    public void deveLancarExcecaoParaTipoDeContaInvalidoOuEmBranco() {
        Conta conta = new Conta(null, "123-x", "a123", "as", BigDecimal.valueOf(1000), 1L);

        Exception excecao = assertThrows(Exception.class, () -> contaGatewayImpl.atualizarConta(conta));

        assertEquals("Tipo da conta inválido ou em branco.", excecao.getMessage());
    }

    @Test
    public void deveAtualizarContaComSucesso() throws Exception {
        Conta conta = new Conta(null, "123-x", "a123", "corrente", BigDecimal.valueOf(1000), 1L);
        Usuario usuarioComConta = new Usuario(1L, "tassio", "12312312312", LocalDate.now());
        ContaEntity contaEntity = new ContaEntity();

        when(contaRepository.findById(conta.id())).thenReturn(Optional.of(contaEntity));
        doAnswer(invocation -> null).when(contaMapper).toEntity(any(Conta.class), any(ContaEntity.class));

        Conta resultado = contaGatewayImpl.atualizarConta(conta);

        assertNotNull(resultado);
        verify(contaRepository).save(contaEntity);
    }


    @Test
    public void deveLancarExcecaoQuandoCpfNulo() {
        Exception excecao = assertThrows(Exception.class, () -> contaGatewayImpl.buscarContaPorCpf(null));

        assertEquals("CPF nulo.", excecao.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoUsuarioNaoEncontrado() throws Exception {
        String cpf = "12345678900";

        when(usuarioGateway.buscarUsuarioPorCpf(cpf)).thenReturn(null);

        Exception excecao = assertThrows(Exception.class, () -> contaGatewayImpl.buscarContaPorCpf(cpf));

        assertEquals("Não existe usuário com esse cpf.", excecao.getMessage());
    }

    @Test
    public void deveBuscarContaPorCpfComSucesso() throws Exception {
        String cpf = "12312312312";
        Usuario usuarioValido = new Usuario(1L, "tassio", "12312312312", LocalDate.now());
        ContaEntity contaEntity = new ContaEntity();
        Conta conta = new Conta(null, "123-x", "a123", "corrente", BigDecimal.valueOf(1000), 1L);

        when(usuarioGateway.buscarUsuarioPorCpf(cpf)).thenReturn(usuarioValido);
        when(contaRepository.findByTitular(usuarioValido.id())).thenReturn(Optional.of(contaEntity));
        when(contaMapper.toDomain(contaEntity)).thenReturn(conta);

        Conta resultado = contaGatewayImpl.buscarContaPorCpf(cpf);

        assertNotNull(resultado);
        assertEquals(conta, resultado);
    }

    @Test
    public void deveLancarExcecaoQuandoIdNulo() {
        Exception excecao = assertThrows(Exception.class, () -> contaGatewayImpl.buscarContaPorId(null));

        assertEquals("Id inválido", excecao.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoContaNaoEncontrada() {
        Long id = 1L;
        Conta conta = new Conta(2L, "123-x", "a123", "corrente", BigDecimal.valueOf(1000), 1L);
        when(contaRepository.findById(id)).thenReturn(Optional.empty());

        Exception excecao = assertThrows(Exception.class, () -> contaGatewayImpl.buscarContaPorId(id));

        assertEquals("Não existe conta com esse id.", excecao.getMessage());
    }

    @Test
    public void deveBuscarContaPorIdComSucesso() throws Exception {
        Long id = 1L;
        ContaEntity contaEntity = new ContaEntity();
        Conta conta = new Conta(1L, "123-x", "a123", "corrente", BigDecimal.valueOf(1000), 1L);

        when(contaRepository.findById(id)).thenReturn(Optional.of(contaEntity));
        when(contaMapper.toDomain(contaEntity)).thenReturn(conta);

        Conta resultado = contaGatewayImpl.buscarContaPorId(id);

        assertNotNull(resultado);
        assertEquals(conta, resultado);
    }

    @Test
    public void deveLancarExcecaoQuandoNumeroContaNulo() {
        Exception excecao = assertThrows(Exception.class, () -> contaGatewayImpl.buscarContaPorNumero(null));

        assertEquals("Número da conta inválido", excecao.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoContaNumeroNaoEncontrada() {
        String numeroConta = "123456";
        Conta conta = new Conta(1L, "123-x", "a123", "corrente", BigDecimal.valueOf(1000), 1L);
        
        when(contaRepository.findByNumeroConta(numeroConta)).thenReturn(Optional.empty());

        Exception excecao = assertThrows(Exception.class, () -> contaGatewayImpl.buscarContaPorNumero(numeroConta));

        assertEquals("Não existe conta com esse número.", excecao.getMessage());
    }

    @Test
    public void deveBuscarContaPorNumeroComSucesso() throws Exception {
        String numeroConta = "123456";
        ContaEntity contaEntity = new ContaEntity();
        Conta conta = new Conta(1L, "123-x", "a123", "corrente", BigDecimal.valueOf(1000), 1L);

        when(contaRepository.findByNumeroConta(numeroConta)).thenReturn(Optional.of(contaEntity));
        when(contaMapper.toDomain(contaEntity)).thenReturn(conta);

        Conta resultado = contaGatewayImpl.buscarContaPorNumero(numeroConta);

        assertNotNull(resultado);
        assertEquals(conta, resultado);
    }





}