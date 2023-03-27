package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "marca", schema = "public")
public class Marca extends Entitty{
    @Getter @Setter
    @Column(name = "nm-marca", nullable = false)
    private String nome;


}
