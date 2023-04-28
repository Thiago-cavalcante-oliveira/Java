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
@Table(name = "configuracoes", schema = "public")
@Audited
@AuditTable(value="configuracoes_audit", schema = "audit")
public class Configuracao extends Entitty{
    @Getter @Setter
    @Column(name = "num-valor-hora", nullable = false)
    private BigDecimal valorHora;
    @Getter @Setter
    @Column(name = "num-valor-minuto-multa", nullable = false)
    private BigDecimal valorMinutoHora;
    @Getter @Setter
    @Column(name = "ds-inicio-atendimento", nullable = false)
    private LocalTime inicioExpediente;
    @Getter @Setter
    @Column(name = "ds-fim-atendimento", nullable = false)
    private LocalTime fimExpediente;
    @Getter @Setter
    @Column(name = "num-gera-desconto", nullable = false)
    private LocalTime tempoParaDesconto;
    @Getter @Setter
    @Column(name = "num-tempo-desconto", nullable = false)
    private LocalTime tempoDeDesconto;
    @Getter @Setter
    @Column(name = "tp-gerar-desconto", nullable = false)
    private boolean gerarDesconto;
    @Getter @Setter
    @Column(name = "num-vaga-moto", nullable = false)
    private int vagasMoto;
    @Getter @Setter
    @Column(name = "num-vaga-carro", nullable = false)
    private int vagasCarro;
    @Getter @Setter
    @Column(name = "num-vaga-van", nullable = false)
    private int vagasVan;



}
