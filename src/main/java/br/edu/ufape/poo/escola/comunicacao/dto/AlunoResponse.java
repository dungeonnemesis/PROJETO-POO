package br.edu.ufape.poo.escola.comunicacao.dto;

import br.edu.ufape.poo.escola.negocio.basica.Aluno;

public record AlunoResponse(Long id, String nome, String cpf, String email, String matricula) {
	public static AlunoResponse de(Aluno aluno) {
		return new AlunoResponse(aluno.getId(), aluno.getNome(), aluno.getCpf(), aluno.getEmail(), aluno.getMatricula());
	}
}
