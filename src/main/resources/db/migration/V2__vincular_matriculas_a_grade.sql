ALTER TABLE IF EXISTS matricula DROP CONSTRAINT IF EXISTS matricula_aluno_id_turma_id_key;

UPDATE matricula m
SET turma_disciplina_id = td.id
FROM turma_disciplina td
WHERE m.turma_disciplina_id IS NULL
  AND m.turma_id = td.turma_id
  AND (SELECT COUNT(*) FROM turma_disciplina td2 WHERE td2.turma_id = m.turma_id) = 1;

ALTER TABLE IF EXISTS matricula
    ADD CONSTRAINT uk_matricula_aluno_grade UNIQUE (aluno_id, turma_disciplina_id);
