package br.com.uniamerica.desafioaluno.desafio;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "enderecos", schema = "public")

public class Endereco {
    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "co-endereco")
    private Long id;
    @Getter @Setter
    @Column(name = "nm-rua", nullable = false, length = 100)
    private String rua;
    @Getter @Setter
    @Column(name = "nm-casa", nullable = false)
    private int numcasa;
    @Getter @Setter
    @Column(name = "nm-bairro", nullable = false)
    private String bairro;
    @Getter @Setter
    @Column(name = "num-cep", length = 9)
    private String cep;
}
