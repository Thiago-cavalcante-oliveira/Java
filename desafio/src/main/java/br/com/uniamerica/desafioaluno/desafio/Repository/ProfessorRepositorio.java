package br.com.uniamerica.desafioaluno.desafio.Repository;

import br.com.uniamerica.desafioaluno.desafio.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepositorio extends JpaRepository <Professor, Long> {
}
