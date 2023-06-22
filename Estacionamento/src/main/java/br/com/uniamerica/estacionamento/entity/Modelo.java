package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Table;
import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "tb_modelos", schema = "public")
@Audited
@AuditTable(value="modelo_audit", schema="audit")
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


