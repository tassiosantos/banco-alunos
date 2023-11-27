package com.ada.banco.domain.model;

import java.time.LocalDate;

public record Usuario(
                        Long id,

                        String nome,
                        String cpf,
                        LocalDate dataNascimento
                      ) {

}
