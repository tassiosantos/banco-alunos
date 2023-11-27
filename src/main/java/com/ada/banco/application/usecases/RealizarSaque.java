package com.ada.banco.application.usecases;

import com.ada.banco.application.gateways.ContaGateway;
import com.ada.banco.application.gateways.TransacaoGateway;
import com.ada.banco.domain.model.Conta;
import com.ada.banco.domain.model.Transacao;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RealizarSaque {

    private final TransacaoGateway transacaoGateway;

    public RealizarSaque(TransacaoGateway transacaoGateway){ this.transacaoGateway = transacaoGateway;}

    public Transacao realizarSaque(Transacao saque) throws Exception {
        return transacaoGateway.fazerSaque(saque);
    }







}
