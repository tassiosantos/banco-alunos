package com.ada.banco.domain.usecase;

import com.ada.banco.domain.gateway.ContaGateway;
import com.ada.banco.domain.model.Conta;

import java.math.BigDecimal;

public class EncerrarConta {
    private ContaGateway contaGateway;

    public EncerrarConta(ContaGateway contaGateway){this.contaGateway = contaGateway;}

    public void execute(Conta conta) throws Exception {
        //Validar se a conta a ser excluída existe
        if(contaGateway.buscarPorCpf(conta.getCpf()) == null){
            throw new Exception("Conta a ser encerrada não existe");
        }

        //Valida se existe saldo na conta antes de excluir
        if(BigDecimal.valueOf(0L) != contaGateway.buscarPorCpf(conta.getCpf()).getSaldo()){
            throw new Exception("A conta precisa ter o saldo zero para ser encerrada.");
        }

        contaGateway.encerrar(conta);
    }


}
