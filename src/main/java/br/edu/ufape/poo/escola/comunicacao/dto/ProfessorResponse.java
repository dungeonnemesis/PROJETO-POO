package br.edu.ufape.poo.escola.comunicacao.dto;

import br.edu.ufape.poo.escola.negocio.basica.Professor;

public record ProfessorResponse(Long id, String nome, String cpf, String email, String especialidade) {
	public static ProfessorResponse de(Professor professor) {
		return new ProfessorResponse(professor.getId(), professor.getNome(), professor.getCpf(), professor.getEmail(), professor.getEspecialidade());
	}
}
