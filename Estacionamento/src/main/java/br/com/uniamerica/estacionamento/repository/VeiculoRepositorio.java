package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository

public interface VeiculoRepositorio extends JpaRepository <Veiculo, Long> {

    @Query("select count(*)>0 from Veiculo veiculo where veiculo.id = :id")
    public boolean checaId(@RequestParam("id")Long id);
    @Query("select marca from Marca marca")
    public List<Marca> listar();

    @Query("select veiculo from Veiculo veiculo where veiculo.ativo = true")
    public List<Veiculo> listarAtivos();
    @Query("select count(*)>0 from Veiculo veiculo where veiculo.placa = :placa")
    public boolean checaPlaca(@RequestParam("placa") String placa);

    @Query("select count(*)>0 from Movimentacao movimentacao where movimentacao.veiculo = :id")
    public boolean checaUso(@RequestParam("id") Long id);

    @Query("select veiculo.id from Veiculo veiculo where veiculo.placa = :placa")
    public Long checaUsoRetornaId(@RequestParam("placa") String placa);

    @Query("select count(*)>0 from Modelo modelo where modelo.id = :id and modelo.ativo=true ")
    public boolean checaAtivoModelo(@RequestParam("id") Long id);

    @Query("select count (*)>0 from Movimentacao movimentacao where movimentacao.veiculo.id = :id and movimentacao.saida = null ")
    public boolean checaMoviemntacaoAbertaSemSaida(@Param("id") Long id);
}

