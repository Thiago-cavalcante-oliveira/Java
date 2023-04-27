package br.com.uniamerica.desafioaluno.desafio.Repository;

import br.com.uniamerica.desafioaluno.desafio.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepositorio extends JpaRepository <Endereco, Long> {
}
