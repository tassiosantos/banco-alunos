package com.ada.banco.application.usecases;

import com.ada.banco.application.gateways.TransacaoGateway;
import com.ada.banco.domain.model.Transacao;
import org.springframework.stereotype.Service;

@Service
public class RealizarDeposito {

    private final TransacaoGateway transacaoGateway;

    public RealizarDeposito(TransacaoGateway transacaoGateway){
        this.transacaoGateway = transacaoGateway;
    }


    public Transacao realizarDeposito(Transacao deposito) throws Exception {
        return transacaoGateway.fazerDeposito(deposito);
    }

}
