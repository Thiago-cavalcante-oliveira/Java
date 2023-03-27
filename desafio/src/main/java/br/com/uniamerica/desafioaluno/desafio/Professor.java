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
    @Getter
    @Setter
    @Column(name = "nm-professor", nullable = false, unique = true, length = 100)
    private String nome;




    @Getter @Setter
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "professor-aluno",
            uniqueConstraints = @UniqueConstraint(
                    columnNames = {
                            "co-professor",
                            "co-aluno"
                    }
            ),
            joinColumns = @JoinColumn(
                    name = "co-professor"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "co-aluno"
            )
    )
    private List<Aluno> alunos;


}
