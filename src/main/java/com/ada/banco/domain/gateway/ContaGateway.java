package com.ada.banco.domain.gateway;

import com.ada.banco.domain.model.Conta;

import java.math.BigDecimal;

public interface ContaGateway {
    Conta buscarPorCpf(String cpf);
    Conta salvar(Conta conta);

    void sacar(Conta conta, BigDecimal valorSaque);

    void encerrar(Conta conta);
}
