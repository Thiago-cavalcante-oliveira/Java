package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;


@Entity
@Table(name = "tb_marcas", schema = "public")
@Audited
@AuditTable(value="marca_audit", schema="audit")
public class Marca extends Entitty{
    @Getter @Setter
    @Column(name = "nm_marca", nullable = false)
    private String nome;


}
