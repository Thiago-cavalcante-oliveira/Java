package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface MovimentacaoRepositorio extends JpaRepository <Movimentacao, Long> {
    @Query("select movi from Movimentacao movi where movi.ativo = true")
    public List<Movimentacao> listarAtivo();

    @Query("select movi from Movimentacao movi where movi.ativo = true and movi.saida = null")
    public List<Movimentacao> listarSemSaida();
    @Query("select movi from Movimentacao movi where movi.id = :id  and movi.saida = null")
    public boolean checaCarroEstacionado(@RequestParam("id") Long id);
}
