package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MarcaRepositorio extends JpaRepository <Marca, Long> {
    @Query("select tabela from Marca as tabela where tabela.nome like :valor ")
    boolean existe(Long valor);
}
