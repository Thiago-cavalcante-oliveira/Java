package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface ConfiguracaoRepositorio extends JpaRepository <Configuracao, Long> {

    @Query("select movimentacao from Movimentacao movimentacao where movimentacao.id = :id")
    public boolean checauso(@RequestParam("id") Long id);
}
