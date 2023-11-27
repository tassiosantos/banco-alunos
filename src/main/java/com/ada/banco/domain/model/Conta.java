package com.ada.banco.domain.model;

import java.math.BigDecimal;

public record Conta(
        Long id,
        String agencia,
        String numeroConta,
        String tipoConta,
        BigDecimal saldo,
        Long titular
    ) {


}
