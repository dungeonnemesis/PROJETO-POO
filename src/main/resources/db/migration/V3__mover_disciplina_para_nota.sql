ALTER TABLE IF EXISTS nota ADD COLUMN IF NOT EXISTS turma_disciplina_id BIGINT;

UPDATE nota n
SET turma_disciplina_id = m.turma_disciplina_id
FROM matricula m
WHERE n.matricula_id = m.id
  AND n.turma_disciplina_id IS NULL
  AND m.turma_disciplina_id IS NOT NULL;

ALTER TABLE IF EXISTS nota
    ADD CONSTRAINT fk_nota_grade FOREIGN KEY (turma_disciplina_id) REFERENCES turma_disciplina (id);

ALTER TABLE IF EXISTS matricula DROP CONSTRAINT IF EXISTS uk_matricula_aluno_grade;
ALTER TABLE IF EXISTS matricula ADD CONSTRAINT uk_matricula_aluno_turma UNIQUE (aluno_id, turma_id);
