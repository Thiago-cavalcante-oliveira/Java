package br.com.uniamerica.desafioaluno.desafio.Repository;

import br.com.uniamerica.desafioaluno.desafio.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepositorio extends JpaRepository <Contato, Long> {
}
