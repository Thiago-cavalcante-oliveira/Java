package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "modelos", schema = "public")
public class Modelo extends Marca {
    @Getter
    @Setter
    @Column(name = "nm-modelo", nullable = false, length = 100)
    private String nome;
    @Getter
    @Setter
    @JoinColumn(name = "nm-marca-carro")
    @ManyToOne
    private Marca marca;
}


