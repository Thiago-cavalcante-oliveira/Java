package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfiguracaoRepositorio extends JpaRepository <Configuracao, Long> {
}
