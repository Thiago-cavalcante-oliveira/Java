package br.com.uniamerica.desafioaluno.desafio;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "contatos")

public class Contato {
    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "co-contato", nullable = false, unique = true)
    private Long id;
    @Getter @Setter
    @Column(name = "tp-telefone", nullable = false, length = 10)
    private String tipo;
    private String valor;
    @Getter @Setter
    @JoinColumn(name = "co-aluno", nullable = false)
    @ManyToOne
    private Aluno aluno;


}
