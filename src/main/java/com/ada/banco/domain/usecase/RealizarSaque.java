package com.ada.banco.domain.usecase;

import com.ada.banco.domain.gateway.ContaGateway;
import com.ada.banco.domain.model.Conta;

import java.math.BigDecimal;

public class RealizarSaque {

    private ContaGateway contaGateway;

    public RealizarSaque(ContaGateway contaGateway){ this.contaGateway = contaGateway;}


    public void execute(Conta conta, BigDecimal valorSaque) throws Exception {
        //Valida se o valor requisitado do saque não é negativo
        if(contaGateway.buscarPorCpf(conta.getCpf()).getSaldo() < 0){
            throw new Exception("Valor a ser sacado deve ser positivo");
        }

        //Valida se o saldo é suficiente para realizar o saque
        if(contaGateway.buscarPorCpf(conta.getCpf()).getSaldo() < valorSaque){
            throw  new Exception("Valor do saque maior que o saldo em conta");
        }


        contaGateway.sacar(conta, valorSaque);


    }


}
