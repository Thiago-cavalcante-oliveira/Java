package br.com.uniamerica.desafioaluno.desafio;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "alunos", schema = "public")
public class Aluno {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "co-aluno", nullable = false, unique = true)
    private Long id;
    @Getter @Setter
    @Column(name = "nm-aluno", nullable = false, length = 100)
    private String nome;
    @Getter @Setter
    @Column(name = "num-cpf", nullable = false, unique = true)
    private Integer cpf;
    @Getter @Setter
    @JoinColumn(name = "ds-endereco",nullable = false)
    @OneToOne
    private Endereco endereco;
}
