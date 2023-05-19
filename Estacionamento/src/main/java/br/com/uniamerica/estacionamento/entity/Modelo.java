package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Table;
import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_modelos", schema = "public")
public class Modelo extends Entitty {
    @Getter
    @Setter
    @Column(name = "nm_modelo", nullable = false, length = 100)
    private String nome;
    @Getter
    @Setter
    @JoinColumn(name = "co_marca")
    @ManyToOne
    private Marca marca;
}


