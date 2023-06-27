package br.com.uniamerica.estacionamento.entity;

import lombok.Getter;
import lombok.Setter;

public class ApiResponse<T> {
    @Getter @Setter
    private T data;
    @Getter @Setter
    private String errorMessage;
}
