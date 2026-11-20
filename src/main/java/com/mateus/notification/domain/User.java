package com.mateus.notification.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private Long id;

    private String firstName;

    private String lastName;

    private String cpf;

    private String phone;

    private Double income;
}
