package br.com.uniamerica.estacionamento.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
@Entity
@Table(name = "Condutores", schema = "public")
public class Condutor extends Entitty {
    @Getter
    @Setter
    @Column(name = "nm-condutor", nullable = false, length = 100)
    private String nome;
    @Getter
    @Setter
    @Column(name = "num-cpf-condutor", nullable = false, unique = true, length = 12)
    private String cpf;
    @Getter
    @Setter
    @Column(name = "num-tel-condutor", nullable = false, length = 17)
    private String telefone;
    @Getter
    @Setter
    @Column(name = "ds-tempo-pago", nullable = false)

    private LocalTime tempoPago;
    @Getter
    @Setter
    @Column(name = "ds-tempo-desconto", nullable = false)
    private LocalTime tempoDesconto;
}


