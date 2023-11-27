package com.ada.banco.application.gateways;

import com.ada.banco.domain.model.Transacao;

public interface TransacaoGateway {

    Transacao fazerSaque(Transacao saque) throws Exception;

    Transacao fazerDeposito(Transacao deposito) throws Exception;

    Transacao fazerTransferencia(Transacao transferencia) throws Exception;
}
