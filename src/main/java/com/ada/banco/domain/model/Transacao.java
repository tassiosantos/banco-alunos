package com.ada.banco.domain.model;

import java.math.BigDecimal;

public record Transacao(
        Long id,
        Long contaOrigem,
        Long contaDestino,
        BigDecimal valor
    ) {

}
