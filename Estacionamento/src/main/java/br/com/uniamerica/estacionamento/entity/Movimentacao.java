package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Entity
@Table(name = "movimentacoes", schema = "public")
public class Movimentacao extends Entitty {
    @Getter
    @Setter
    @JoinColumn(name = "co-veiculo")
    @ManyToOne
    private Veiculo veiculo;
    @Getter
    @Setter
    @JoinColumn(name = "co-condutor")
    @ManyToOne
    private Condutor condutor;
    @Getter
    @Setter
    @Column(name = "dt-entrada", nullable = false)
    private LocalDateTime entrada;
    @Getter
    @Setter
    @Column(name = "dt-saida")
    private LocalDateTime saida;
    @Getter
    @Setter
    @Column(name = "ds-tempo-uso")
    private Long tempo;
    @Getter
    @Setter
    @Column(name = "ds-tempo-desconto")
    private Long tempoDesconto;
    @Getter
    @Setter
    @Column(name = "ds-tempo-multa")
    private Long tempoMulta;
    @Getter
    @Setter
    @Column(name = "vl-desconto")
    private BigDecimal valorDesconto;
    @Getter
    @Setter
    @Column(name = "ds-valor-multa")
    private BigDecimal valorMulta;
    @Getter
    @Setter
    @Column(name = "ds-valor-total")
    private BigDecimal valorTotal;
    @Getter
    @Setter
    @Column(name = "ds-valor-hora")
    private BigDecimal valorHora;
    @Getter
    @Setter
    @Column(name = "ds-valor-hora-multa")
    private BigDecimal valorHoraMulta;

    @Getter
    @Setter
    @JoinColumn(name = "co_configuracao")
    @OneToOne
    private Configuracao configuracao;



}
