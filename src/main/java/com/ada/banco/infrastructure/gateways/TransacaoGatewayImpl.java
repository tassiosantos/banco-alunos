package com.ada.banco.infrastructure.gateways;

import com.ada.banco.application.gateways.TransacaoGateway;
import com.ada.banco.domain.model.Transacao;
import com.ada.banco.domain.model.Conta;
import com.ada.banco.infrastructure.persistence.entities.ContaEntity;
import com.ada.banco.infrastructure.persistence.entities.TransacaoEntity;
import com.ada.banco.infrastructure.persistence.repositories.TransacaoRepository;

import java.math.BigDecimal;



public class TransacaoGatewayImpl implements TransacaoGateway {

    private ContaGatewayImpl contaGateway;

    private  ContaMapper contaMapper;
    private TransacaoMapper transacaoMapper;

    private TransacaoRepository transacaoRepository;


    public  TransacaoGatewayImpl(ContaGatewayImpl contaGateway,
                             ContaMapper contaMapper,
                             TransacaoMapper transacaoMapper,
                             TransacaoRepository transacaoRepository){
        this.contaGateway = contaGateway;
        this.contaMapper = contaMapper;
        this.transacaoMapper = transacaoMapper;
        this.transacaoRepository = transacaoRepository;
    }

    @Override
    public Transacao fazerSaque(Transacao saque) throws Exception {
        if(saque.contaDestino() != null){
            throw new Exception("Não é possível ter conta de destino em um saque");
        }

        if(saque.contaOrigem() == null){
            throw  new Exception("Não é possível realizar um saque sem conta de origem");
        }

        if (saque.valor().compareTo(BigDecimal.valueOf(0)) <= 0){
            throw  new Exception("Não é possível realizar um saque com valor zero ou negativo");
        }

        if(saque.valor().compareTo(contaGateway.buscarContaPorId(saque.contaOrigem()).saldo()) > 0){
            throw  new Exception("Saldo insuficiente.");
        }

        ContaEntity contaEntity = new ContaEntity();
        contaMapper.toEntity(contaGateway.buscarContaPorId(saque.contaOrigem()), contaEntity);
        contaEntity.sacar(saque.valor());
        Conta conta = contaMapper.toDomain(contaEntity);
        contaGateway.atualizarConta(conta);

        TransacaoEntity saqueEntity = new TransacaoEntity();
        transacaoMapper.toEntity(saque, saqueEntity);


        return transacaoMapper.toDomain(transacaoRepository.save(saqueEntity));
    }

    @Override
    public Transacao fazerDeposito(Transacao deposito) throws Exception {
        if(deposito.contaOrigem() != null){
            throw new Exception("Não é possível ter conta de origem em um depósito");
        }

        if(deposito.contaDestino() == null){
            throw new Exception("Não é possível realizar um depósito sem conta de destino");
        }

        if (deposito.valor().compareTo(BigDecimal.valueOf(0)) <= 0){
            throw  new Exception("Não é possível realizar um depósito com valor zero ou negativo");
        }

        ContaEntity contaEntity = new ContaEntity();
        contaMapper.toEntity(contaGateway.buscarContaPorId(deposito.contaOrigem()), contaEntity);

        contaEntity.depositar(deposito.valor());
        Conta conta = contaMapper.toDomain(contaEntity);
        contaGateway.atualizarConta(conta);

        TransacaoEntity saqueEntity = new TransacaoEntity();
        transacaoMapper.toEntity(deposito, saqueEntity);


        return transacaoMapper.toDomain(transacaoRepository.save(saqueEntity));
    }
    @Override
    public Transacao fazerTransferencia(Transacao transferencia) throws Exception {
        if(transferencia.contaDestino() == null){
            throw new Exception("Não é possível realizar uma tranferência sem conta de destino");
        }

        if(transferencia.contaOrigem() == null){
            throw new Exception("Não é possível realizar uma tranferência sem conta de origem");
        }

        if (transferencia.valor().compareTo(BigDecimal.valueOf(0)) <= 0){
            throw  new Exception("Não é possível realizar uma transferência com valor zero ou negativo");
        }

        if(transferencia.valor().compareTo(contaGateway.buscarContaPorId(transferencia.contaOrigem()).saldo()) < 0){
            throw  new Exception("Saldo insuficiente na conta de Origem.");
        }

        ContaEntity contaOrigemEntity = new ContaEntity();
        contaMapper.toEntity(contaGateway.buscarContaPorId(transferencia.contaOrigem()), contaOrigemEntity);
        contaOrigemEntity.sacar(transferencia.valor());
        Conta contaOrigem = contaMapper.toDomain(contaOrigemEntity);
        contaGateway.atualizarConta(contaOrigem);



        ContaEntity contaDestinoEntity = new ContaEntity();
        contaMapper.toEntity(contaGateway.buscarContaPorId(transferencia.contaOrigem()), contaDestinoEntity);

        contaDestinoEntity.depositar(transferencia.valor());
        Conta contaDestino = contaMapper.toDomain(contaDestinoEntity);
        contaGateway.atualizarConta(contaDestino);

        TransacaoEntity transferenciaEntity = new TransacaoEntity();
        transacaoMapper.toEntity(transferencia, transferenciaEntity);

        return transacaoMapper.toDomain(transacaoRepository.save(transferenciaEntity));
    }
}
