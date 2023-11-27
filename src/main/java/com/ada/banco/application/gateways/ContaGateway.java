package com.ada.banco.application.gateways;

import com.ada.banco.domain.model.Conta;

public interface ContaGateway {

    Conta criarConta(Conta conta) throws Exception;

    Conta atualizarConta(Conta conta) throws  Exception;
    Conta buscarContaPorCpf(String cpf) throws Exception;

    Conta buscarContaPorId(Long id) throws Exception;

    Conta buscarContaPorNumero(String numeroConta) throws Exception;



}
