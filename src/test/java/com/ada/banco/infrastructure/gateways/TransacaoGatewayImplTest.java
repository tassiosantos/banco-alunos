package com.ada.banco.infrastructure.gateways;

import com.ada.banco.domain.model.Conta;
import com.ada.banco.domain.model.Transacao;
import com.ada.banco.domain.model.Usuario;
import com.ada.banco.infrastructure.persistence.entities.UsuarioEntity;
import com.ada.banco.infrastructure.persistence.repositories.TransacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import com.ada.banco.infrastructure.persistence.entities.ContaEntity;
import com.ada.banco.infrastructure.persistence.entities.TransacaoEntity;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransacaoGatewayImplTest {

    @Mock
    private ContaGatewayImpl contaGateway;

    @Mock
    private ContaMapper contaMapper;

    @Mock
    private TransacaoMapper transacaoMapper;

    @Mock
    private TransacaoRepository transacaoRepository;

    @InjectMocks
    private TransacaoGatewayImpl transacaoGatewayImpl;

//    @Test
//    public void deveFazerSaqueComSucesso() throws Exception {
//        // Dados de entrada
//        Transacao saque = new Transacao(1L, 1L, null, BigDecimal.valueOf(400));
//        Conta contaOrigem = new Conta(1L,"w123","123W","corrente", BigDecimal.valueOf(1000L),1L);
//        Conta contaDestino = new Conta(1L,"w123","123W","corrente", BigDecimal.valueOf(1000L),1L);
//        Usuario usuarioComConta = new Usuario(1L, "tassio", "12312312312", LocalDate.now());
//        UsuarioEntity usuarioOrigemEntity = new UsuarioEntity("tassio", "12312312312", LocalDate.now());
//        UsuarioEntity usuarioDestinoEntity = new UsuarioEntity("tassio2", "12312312111", LocalDate.now());
//        ContaEntity contaOrigemEntity = new ContaEntity(1L,"w123","123W3","corrente", BigDecimal.valueOf(1000),usuarioOrigemEntity);
//        ContaEntity contaDestinoEntity = new ContaEntity(3L,"w123","123W3","corrente", BigDecimal.valueOf(1000),usuarioDestinoEntity);
//        TransacaoEntity saqueEntity = new TransacaoEntity(1L, contaOrigemEntity, null, BigDecimal.valueOf(400));
//
//        when(contaGateway.buscarContaPorId(saque.contaOrigem())).thenReturn(contaOrigem);
//        doAnswer(invocation -> null).when(contaMapper).toEntity(any(Conta.class), any(ContaEntity.class));
//        doAnswer(invocation -> null).when(transacaoMapper).toEntity(saque, saqueEntity);
//
//        when(transacaoRepository.save(saqueEntity)).thenReturn(saqueEntity);
//        when(transacaoMapper.toDomain(saqueEntity)).thenReturn(saque);
//
//
//        Transacao resultado = transacaoGatewayImpl.fazerSaque(saque);
//
//
//        assertNotNull(resultado);
//        assertEquals(saque, resultado);
//    }

    @Test
    public void deveLancarExcecaoQuandoContaDestinoNaoNulaEmSaque() {
        Transacao saque = new Transacao(1L, 1L, 1L, BigDecimal.valueOf(400));

        Exception excecao = assertThrows(Exception.class, () -> transacaoGatewayImpl.fazerSaque(saque));

        assertEquals("Não é possível ter conta de destino em um saque", excecao.getMessage());
    }

}