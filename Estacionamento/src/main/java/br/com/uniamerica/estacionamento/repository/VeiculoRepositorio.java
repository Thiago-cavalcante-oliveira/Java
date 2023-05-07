package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository

public interface VeiculoRepositorio extends JpaRepository <Veiculo, Long> {

    @Query("select veiculo from Veiculo veiculo where veiculo.id != :id")
    public boolean checaId(@RequestParam("id")Long id);
    @Query("select marca from Marca marca")
    public List<Marca> listar();

    @Query("select veiculo from Veiculo veiculo where veiculo.ativo = true")
    public List<Marca> listarAtivos();
    @Query("select veiculo from Veiculo veiculo where veiculo.placa = :placa")
    public boolean checaPlaca(@PathVariable("placa") String placa);

    @Query("select movimentacao from Movimentacao movimentacao where movimentacao.veiculo = :id")
    public boolean checaUso(@RequestParam("id") Long id);

}

