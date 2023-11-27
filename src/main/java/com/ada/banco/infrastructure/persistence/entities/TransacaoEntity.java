package com.ada.banco.infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class TransacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private ContaEntity contaOrigem;

    @OneToOne
    private ContaEntity contaDestino;

    private BigDecimal valor;

    public TransacaoEntity(Long id, ContaEntity contaOrigem, ContaEntity contaDestino, BigDecimal valor) {
        this.id = id;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
    }

    public TransacaoEntity() {

    }
}
