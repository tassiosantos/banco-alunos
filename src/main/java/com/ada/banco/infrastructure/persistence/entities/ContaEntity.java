package com.ada.banco.infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class ContaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String agencia;

    private String numeroConta;

    private String tipoConta;

    private BigDecimal saldo;

    @OneToOne
    private UsuarioEntity titular;

    public ContaEntity(
            Long id,
            String agencia,
            String numeroConta,
            String tipoConta,
            BigDecimal saldo,
            UsuarioEntity titular) {
        this.id = id;
        this.agencia = agencia;
        this.numeroConta = numeroConta;
        this.tipoConta = tipoConta;
        this.saldo = saldo;
        this.titular = titular;
    }

    public ContaEntity() {

    }

    public Long getId() {
        return id;
    }

    public String getAgencia() {
        return agencia;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public UsuarioEntity getTitular() {
        return titular;
    }

    public void sacar(BigDecimal valor){
        this.saldo = this.saldo.subtract(valor);
    }

    public void depositar(BigDecimal valor){
        this.saldo = this.saldo.add(valor);
    }
}
