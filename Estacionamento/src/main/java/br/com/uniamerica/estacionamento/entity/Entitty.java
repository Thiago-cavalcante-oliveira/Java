package br.com.uniamerica.estacionamento.entity;
import jakarta.persistence.*;
import  jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @autor Thiago Cavalcante
 *
 * @version 1.0.0.0 20/03/2023
 * @since 1.0.0
 */
@MappedSuperclass//indica para o hbernate que esta é a classe a ser implementada em todas as outras
public abstract class Entitty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Column(name = "id", nullable = false, unique = true)
    protected Long id;
    @Getter @Setter
    @Column(name = "dtCadastro", nullable=false )
    private LocalDateTime cadastro;
    @Getter @Setter
    @Column(name = "dtEdicao")
    private LocalDateTime edicao;
    @Column(name = "stAtivo", nullable=false )
    @Getter @Setter
    private boolean ativo;


    @PrePersist
    private void prePersist (){
        this.cadastro = LocalDateTime.now();
        this.ativo = true;
    }

    @PreUpdate
        private void preUpdate(){
        this.edicao = LocalDateTime.now();
    }

}
