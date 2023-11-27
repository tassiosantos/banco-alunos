package com.ada.banco.infrastructure.gateways;

import com.ada.banco.domain.model.Transacao;
import com.ada.banco.infrastructure.persistence.entities.TransacaoEntity;
import com.ada.banco.infrastructure.persistence.repositories.TransacaoRepository;

public class TransacaoEntityMapper {

    private TransacaoRepository transacaoRepository;
    private ContaEntityMapper contaEntityMapper;

    private ContaGatewayImpl contaGateway;

    public TransacaoEntityMapper(
            TransacaoRepository transacaoRepository,
            ContaEntityMapper contaEntityMapper,
            ContaGatewayImpl contaGateway
    ){
        this.transacaoRepository = transacaoRepository;
        this.contaEntityMapper = contaEntityMapper;
        this.contaGateway = contaGateway;
    }


//    public TransacaoEntity toEntity(Transacao transacao){
//        TransacaoEntity transacaoEntity = new TransacaoEntity(
//
//        );
//
//    }




}
