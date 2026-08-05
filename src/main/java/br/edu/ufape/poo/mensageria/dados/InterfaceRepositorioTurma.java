package br.edu.ufape.poo.mensageria.dados;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufape.poo.mensageria.negocio.basica.Turma;

@Repository
public interface InterfaceRepositorioTurma extends JpaRepository<Turma, Long> {
}
