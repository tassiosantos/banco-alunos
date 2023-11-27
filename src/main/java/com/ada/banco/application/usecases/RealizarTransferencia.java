package com.ada.banco.application.usecases;

import com.ada.banco.application.gateways.TransacaoGateway;
import com.ada.banco.domain.model.Transacao;
import org.springframework.stereotype.Service;

@Service
public class RealizarTransferencia {

    private final TransacaoGateway transacaoGateway;


    public RealizarTransferencia(TransacaoGateway transacaoGateway){
        this.transacaoGateway = transacaoGateway;
    }


    public Transacao realizarTransferencia(Transacao transferencia) throws Exception {
        return this.transacaoGateway.fazerTransferencia(transferencia);
    }


}
