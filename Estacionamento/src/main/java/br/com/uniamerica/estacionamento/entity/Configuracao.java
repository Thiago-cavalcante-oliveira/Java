package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Table(name = "tb_configuracoes", schema = "public")
@Audited
@AuditTable(value="configuracoes_audit", schema = "audit")
public class Configuracao extends Entitty{
    @Getter @Setter
    @Column(name = "vl_hora", nullable = false)
    private BigDecimal valorHora;
    @Getter @Setter
    @Column(name = "vl_minuto_multa", nullable = false)
    private BigDecimal valorMinutoMulta;
    @Getter @Setter
    @Column(name = "inicio_atendimento", nullable = false)
    private LocalTime inicioExpediente;
    @Getter @Setter
    @Column(name = "fim_atendimento", nullable = false)
    private LocalTime fimExpediente;
    @Getter @Setter
    @Column(name = "num_gera_desconto", nullable = false)
    private Long tempoParaGerarDesconto;
    @Getter @Setter
    @Column(name = "num_tempo_desconto", nullable = false)
    private Long tempoDeCreditoDesconto;
    @Getter @Setter
    @Column(name = "st_gerar_desconto", nullable = false)
    private boolean gerarDesconto;
    @Getter @Setter
    @Column(name = "num_vaga_moto", nullable = false)
    private int vagasMoto;
    @Getter @Setter
    @Column(name = "num_vaga_carro", nullable = false)
    private int vagasCarro;
    @Getter @Setter
    @Column(name = "num_vaga_van", nullable = false)
    private int vagasVan;
    @Getter @Setter
    @Column(name ="nm_versao", nullable = false)
    private int versao;

}
