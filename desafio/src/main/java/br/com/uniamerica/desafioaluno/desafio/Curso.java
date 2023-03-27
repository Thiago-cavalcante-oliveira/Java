package br.com.uniamerica.desafioaluno.desafio;
import java.util.List;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Entity
@Table(name = "cursos", schema = "public")
public class Curso {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "co-curso", nullable = false, unique = true)
    private Long id;
    @Getter @Setter
    @Column(name = "nm-curso", nullable = false, length = 40)
    private String nome;
    @Getter @Setter
    @Column(name = "ds-carga-horaria", nullable = false)
    private int cargaHoraria;
    @Getter
    @Column(name = "nm-aluno")
    @ManyToMany
    private List<Aluno> aluno;
}
