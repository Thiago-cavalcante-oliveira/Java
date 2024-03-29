package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Table(name = "tb_condutor", schema = "public")
@Audited
@AuditTable(value = "condutor_audit", schema = "audit")
public class Condutor extends Entitty {
    @Getter
    @Setter
    @Column(name = "nm_condutor", nullable = false, length = 100)
    private String nome;
    @Getter
    @Setter
    @Column(name = "num_cpf_condutor", nullable = false, unique = true, length = 15)
    private String cpf;
    @Getter
    @Setter
    @Column(name = "num_tel_condutor", nullable = false, length = 19)
    private String telefone;
    @Getter
    @Setter
    @Column(name = "tempo_pago_minuto", columnDefinition = "integer default 0")
    private Long tempoPagoEmMinuto = 0L;

    @Getter
    @Setter

    @Column(name = "tempo_pago_hora", columnDefinition = "integer default 0")
    private Long tempoPagoEmHora=  0L;
    @Getter
    @Setter
    @Column(name = "tempo_desconto", columnDefinition = "integer default 0")
    private Long tempoDesconto = 0L;
}


