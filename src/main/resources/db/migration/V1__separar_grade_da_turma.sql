CREATE TABLE IF NOT EXISTS turma_disciplina (
    id BIGSERIAL PRIMARY KEY,
    turma_id BIGINT NOT NULL,
    disciplina_id BIGINT NOT NULL,
    professor_id BIGINT NOT NULL,
    CONSTRAINT uk_turma_disciplina UNIQUE (turma_id, disciplina_id),
    CONSTRAINT fk_turma_disciplina_turma FOREIGN KEY (turma_id) REFERENCES turma (id),
    CONSTRAINT fk_turma_disciplina_disciplina FOREIGN KEY (disciplina_id) REFERENCES disciplina (id),
    CONSTRAINT fk_turma_disciplina_professor FOREIGN KEY (professor_id) REFERENCES professor (id)
);

DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'turma' AND column_name = 'disciplina_id')
       AND EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'turma' AND column_name = 'professor_id') THEN
        INSERT INTO turma_disciplina (turma_id, disciplina_id, professor_id)
        SELECT turma.id, turma.disciplina_id, turma.professor_id
        FROM turma
        WHERE turma.disciplina_id IS NOT NULL AND turma.professor_id IS NOT NULL
        ON CONFLICT (turma_id, disciplina_id) DO NOTHING;
    END IF;
END $$;

ALTER TABLE IF EXISTS turma DROP COLUMN IF EXISTS disciplina_id CASCADE;
ALTER TABLE IF EXISTS turma DROP COLUMN IF EXISTS professor_id CASCADE;
ALTER TABLE IF EXISTS matricula ADD COLUMN IF NOT EXISTS turma_disciplina_id BIGINT;
ALTER TABLE IF EXISTS matricula ADD CONSTRAINT fk_matricula_grade FOREIGN KEY (turma_disciplina_id) REFERENCES turma_disciplina (id);