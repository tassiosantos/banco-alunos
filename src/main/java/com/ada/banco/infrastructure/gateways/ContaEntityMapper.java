package com.ada.banco.infrastructure.gateways;

import com.ada.banco.domain.model.Conta;
import com.ada.banco.infrastructure.persistence.entities.ContaEntity;

public class ContaEntityMapper {

    private UsuarioGatewayImpl usuarioGatewayImpl;
    private UsuarioEntityMapper usuarioEntityMapper;

    public ContaEntityMapper(UsuarioGatewayImpl usuarioGatewayImpl, UsuarioEntityMapper usuarioEntityMapper){
        this.usuarioGatewayImpl = usuarioGatewayImpl;
        this.usuarioEntityMapper = usuarioEntityMapper;
    }

    public ContaEntity toEntity(Conta contaDominio) throws Exception {
        ContaEntity contaEntity = new ContaEntity(
                contaDominio.id(),
                contaDominio.agencia(),
                contaDominio.numeroConta(),
                contaDominio.tipoConta(),
                contaDominio.saldo(),
                usuarioEntityMapper.toEntity(usuarioGatewayImpl.buscarUsuarioPorId(contaDominio.titular()))
        );

        return contaEntity;
    }


    public Conta toDomain(ContaEntity contaEntity) {
        Conta conta = new Conta(
                contaEntity.getId(),
                contaEntity.getAgencia(),
                contaEntity.getNumeroConta(),
                contaEntity.getTipoConta(),
                contaEntity.getSaldo(),
                contaEntity.getTitular().getId()

        );
        return conta;
    }



}
