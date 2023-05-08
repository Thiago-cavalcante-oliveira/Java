package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface ModeloRepositorio extends JpaRepository <Modelo, Long> {

    @Query("SELECT modelo from Modelo modelo where modelo.ativo = true" )//traz a lista do que está ativo
    List<Modelo> modeloAtivo();

    @Query("SELECT modelo from Modelo modelo")//traz a lista completa
    List<Modelo> listaModelos();
    @Query("SELECT count(*)>0 FROM   Veiculo veiculo where  veiculo.modelo = :id")
    public boolean checaUso(@RequestParam("id")Long id);

    @Query("SELECT count(*)>0 FROM Modelo modelo WHERE modelo.nome = :nome")//checa se o nome a ser cadastrado já existe no banco
    public boolean checaNomeModelo(@PathVariable("nome") String nome);

    @Query("SELECT count(*)>0 FROM Modelo modelo where modelo.id != :id")//checa se o ID existe no banco
    public boolean ChecaId(@RequestParam("id")Long id);


    @Modifying
    @Transactional
    @Query("UPDATE Modelo modelo set modelo.ativo = false where :id = modelo.id")
    public Modelo deletarModelo(final Long id);


}
