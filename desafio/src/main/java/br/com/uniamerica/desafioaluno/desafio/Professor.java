package br.com.uniamerica.desafioaluno.desafio;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "professores", schema = "public")

public class Professor {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "co-professor", nullable = false, unique = true)
    private Long id;
    @Getter @Setter
    @Column(name = "nm-professor", nullable = false, unique = true, length = 100)
    private String nome;
    @Getter @Setter
    @JoinColumn(name = "co-aluno")
    @OneToMany
    private List<Aluno> aluno;
}
