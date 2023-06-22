package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_movimentacoes", schema = "public")
@Audited
@AuditTable(value="movimentacao_audit", schema="audit")
public class Movimentacao extends Entitty {
    @Getter
    @Setter
    @JoinColumn(name = "co_veiculo")
    @ManyToOne
    private Veiculo veiculo;
    @Getter
    @Setter
    @JoinColumn(name = "co_condutor")
    @ManyToOne
    private Condutor condutor;
    @Getter
    @Setter
    @Column(name = "dt_entrada", nullable = false)
    private LocalDateTime entrada;
    @Getter
    @Setter
    @Column(name = "dt_saida")
    private LocalDateTime saida;
    @Getter
    @Setter
    @Column(name = "ds_tempo_uso")
    private Long tempo;
    @Getter
    @Setter
    @Column(name = "ds_tempo_desconto")
    private Long tempoDesconto;
    @Getter
    @Setter
    @Column(name = "ds_tempo_multa")
    private Long tempoMulta;
    @Getter
    @Setter
    @Column(name = "vl_desconto")
    private BigDecimal valorDesconto;
    @Getter
    @Setter
    @Column(name = "ds_valor_multa")
    private BigDecimal valorMulta;
    @Getter
    @Setter
    @Column(name = "ds_valor_total")
    private BigDecimal valorTotal;
    @Getter
    @Setter
    @Column(name = "ds_valor_hora")
    private BigDecimal valorHora;
    @Getter
    @Setter
    @Column(name = "ds_valor_hora_multa")
    private BigDecimal valorHoraMulta;

    @Getter
    @Setter
    @JoinColumn(name = "co_configuracao")
    @ManyToOne
    private Configuracao configuracao;

    @Getter
    @Setter
    @Column(name = "versao_configuracao")
    private int versao;



}
