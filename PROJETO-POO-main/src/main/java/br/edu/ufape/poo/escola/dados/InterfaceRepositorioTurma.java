package br.edu.ufape.poo.escola.dados;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufape.poo.escola.negocio.basica.Turma;

@Repository
public interface InterfaceRepositorioTurma extends JpaRepository<Turma, Long> {
}
