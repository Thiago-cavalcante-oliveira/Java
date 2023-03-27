package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepositorio extends JpaRepository <Veiculo, Long> {
}
