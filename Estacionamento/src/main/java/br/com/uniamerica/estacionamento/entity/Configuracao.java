package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private BigDecimal valorMinutoHora;
    @Getter @Setter
    @Column(name = "inicio_atendimento", nullable = false)
    private LocalTime inicioExpediente;
    @Getter @Setter
    @Column(name = "fim_atendimento", nullable = false)
    private LocalTime fimExpediente;
    @Getter @Setter
    @Column(name = "num_gera_desconto", nullable = false)
    private LocalTime tempoParaDesconto;
    @Getter @Setter
    @Column(name = "num_tempo_desconto", nullable = false)
    private LocalTime tempoDeDesconto;
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



}
