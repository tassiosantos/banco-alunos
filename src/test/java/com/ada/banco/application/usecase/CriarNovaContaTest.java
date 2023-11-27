package com.ada.banco.application.usecase;

import com.ada.banco.application.gateways.ContaGateway;
import com.ada.banco.application.usecases.CriarNovaConta;
import com.ada.banco.domain.model.Conta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import java.math.BigDecimal;


@ExtendWith(MockitoExtension.class)
public class CriarNovaContaTest {

    @Mock
    private ContaGateway contaGateway;

    @InjectMocks
    private CriarNovaConta criarNovaConta;

    @Test
    public void deveCriarContaComSucesso() throws Exception {

        // Given
        Conta conta = new Conta(null,"123456789", "12345-x", "corrente",BigDecimal.valueOf(1000),1L);

        // When
        when(contaGateway.criarConta(any())).thenReturn(conta);

        Conta contaCriada = criarNovaConta.criarConta(conta);

        // Then
        Assertions.assertNotNull(contaCriada, "A conta criada não deve ser nula");
        Assertions.assertEquals(conta, contaCriada, "A conta criada deve ser igual à conta fornecida");
        verify(contaGateway, times(1)).criarConta(conta);
    }
}