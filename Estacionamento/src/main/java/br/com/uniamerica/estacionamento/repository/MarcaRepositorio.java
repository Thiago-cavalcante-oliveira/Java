package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Repository

public interface MarcaRepositorio extends JpaRepository <Marca, Long> {
    @Query("select count(*)>0 from Marca marca  where marca.nome = :nome ")
    public boolean checaNome(@RequestParam("nome")String nome);

    @Query("select count (*)>0 from Marca marca where marca.id = :id")
    public boolean checaId(@RequestParam("id")Long id);
    @Query("select marca from Marca marca")
    public List<Marca> listaMarcas();

    @Query("select marca from Marca marca where marca.ativo = true")
    public List<Marca> listaMarcasAtivas();
    @Query("select count(*)>0 from Marca marca where marca.id = :id and marca.ativo = true ")
    public boolean marcaAtiva(@RequestParam("id")Long id);

    @Query("select count(*)>0 from Modelo modelo where modelo.marca.id = :id")
    public boolean checaUso(@RequestParam("id") Long id);
    @Query ("select marca.id from Marca marca where marca.id = :id")
    public Long checaUsaoRetornaIDMarca(@RequestParam("id")Long id);
}
