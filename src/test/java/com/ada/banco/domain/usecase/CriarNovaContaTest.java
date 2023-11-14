package com.ada.banco.domain.usecase;

import com.ada.banco.domain.gateway.ContaGateway;
import com.ada.banco.domain.model.Conta;
import com.ada.banco.dummy.ContaGatewayDummyImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class CriarNovaContaTest {

    @Test
    public  void deveLancarUmaExceptionCasoUsuarioJaPossuaConta(){

        ContaGateway contaGatewayDummy = new ContaGatewayDummyImpl();

        CriarNovaConta criarNovaConta = new CriarNovaConta(contaGatewayDummy);

        Conta novaConta = new Conta(101L, 0001L, 1234L, new BigDecimal("1000.00"), "Alice", "12345678900");


        Assertions.assertThrows(Exception.class, () -> criarNovaConta.execute(novaConta));

    }



}
