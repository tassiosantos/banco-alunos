package com.ada.banco.domain.usecase;

import com.ada.banco.domain.gateway.ContaGateway;
import com.ada.banco.domain.model.Conta;
import com.ada.banco.dummy.ContaGatewayDummyImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class EncerrarContaTest {


    @Test
    public void deveLancarUmaExceptionCasoAContaNaoExista(){
        ContaGateway contaGateway = new ContaGatewayDummyImpl();

        EncerrarConta encerrarConta = new EncerrarConta(contaGateway);

        Conta contaInvalida = new Conta(101L, 0001L, 1234L, new BigDecimal("1000.00"), "Alice", "12345678987");

        Assertions.assertThrows(Exception.class, () -> encerrarConta.execute(contaInvalida));


    }

    @Test
    public void deveLancarUmaExceptionCasoOSaldoNaoSejaZero(){
        ContaGateway contaGateway = new ContaGatewayDummyImpl();

        EncerrarConta encerrarConta = new EncerrarConta(contaGateway);

        Conta contaComSaldo = contaGateway.buscarPorCpf("12345678900");

        Assertions.assertThrows(Exception.class, () -> encerrarConta.execute(contaComSaldo));

    }

    @Test
    public void deveEncerrarUmaContaComSucesso() throws Exception {
        ContaGateway contaGateway = new ContaGatewayDummyImpl();

        EncerrarConta encerrarConta = new EncerrarConta(contaGateway);

        Conta contaComSaldo = contaGateway.buscarPorCpf("12345678900");
        contaComSaldo.setSaldo(BigDecimal.valueOf(0L));

        encerrarConta.execute(contaComSaldo);

        Assertions.assertNull(contaGateway.buscarPorCpf("12345678900"));

    }

}
