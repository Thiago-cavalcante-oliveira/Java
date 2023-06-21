package br.com.uniamerica.estacionamento.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.context.annotation.EnableMBeanExport;

import java.time.LocalDateTime;
import java.time.LocalTime;
@Entity

@Table(name = "tb_condutores", schema = "public")
//@Audited
//@AuditTable(value = "condutores_audit", schema = "audit")
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
    @Column(name = "tempo_pago_minuto", columnDefinition = "default = 0")
    private Long tempoPagoEmMinuto ;

    @Getter
    @Setter
    @Column(name = "tempo_pago_hora" ,columnDefinition = "default = 0")
    private Long tempoPagoEmHora;
    @Getter
    @Setter
    @Column(name = "tempo_desconto",columnDefinition = "default = 0")
    private Long tempoDesconto;
}


