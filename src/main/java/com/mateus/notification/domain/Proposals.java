package com.mateus.notification.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Proposals {

    private Long id;

    private Double valorSolicitado;

    private int prazoPagamento;

    private Boolean aprovada;

    private boolean integrada;

    private String observacao;

    private Users usuario;

}
