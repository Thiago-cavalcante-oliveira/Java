package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepositorio extends JpaRepository <Movimentacao, Long> {
}
