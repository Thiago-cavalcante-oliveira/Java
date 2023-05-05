package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Table(name = "veiculos", schema = "public")
public class Veiculo extends Entitty{
    @Getter @Setter
    @Column(name = "num_placa_veiculo", nullable = false, length = 7)
    private String placa;
    @Getter @Setter
    @JoinColumn(name = "co_modelo", nullable = false)
    @ManyToOne
    private Modelo modelo;
    @Getter @Setter
    @Column(name = "tp_cor", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Cor cor;
    @Getter @Setter
    @Column(name = "tp_veiculo", nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    @Getter @Setter
    @Column(name = "num_ano_veiculo", nullable = false)
    private int ano;

}


