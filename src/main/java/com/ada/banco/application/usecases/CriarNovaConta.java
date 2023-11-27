package com.ada.banco.application.usecases;

import com.ada.banco.application.gateways.ContaGateway;
import com.ada.banco.domain.model.Conta;
import org.springframework.stereotype.Service;

@Service
public class CriarNovaConta {
    private final ContaGateway contaGateway;

    public CriarNovaConta(ContaGateway contaGateway) {
        this.contaGateway = contaGateway;
    }

    public Conta criarConta(Conta conta) throws Exception {
        return contaGateway.criarConta(conta);
    }
}
