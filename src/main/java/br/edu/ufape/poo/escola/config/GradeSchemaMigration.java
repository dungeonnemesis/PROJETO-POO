package br.edu.ufape.poo.escola.config;

import javax.sql.DataSource;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class GradeSchemaMigration {
	@Bean
	ApplicationRunner ensureGradeSchema(DataSource dataSource) {
		return args -> {
			JdbcTemplate jdbc = new JdbcTemplate(dataSource);
			jdbc.execute("CREATE TABLE IF NOT EXISTS turma_disciplina (id BIGSERIAL PRIMARY KEY, turma_id BIGINT NOT NULL, disciplina_id BIGINT NOT NULL, professor_id BIGINT NOT NULL, CONSTRAINT uk_turma_disciplina UNIQUE (turma_id, disciplina_id), CONSTRAINT fk_grade_turma FOREIGN KEY (turma_id) REFERENCES turma (id), CONSTRAINT fk_grade_disciplina FOREIGN KEY (disciplina_id) REFERENCES disciplina (id), CONSTRAINT fk_grade_professor FOREIGN KEY (professor_id) REFERENCES professor (id))");
			Integer oldColumns = jdbc.queryForObject("SELECT COUNT(*) FROM information_schema.columns WHERE table_name = 'turma' AND column_name IN ('disciplina_id', 'professor_id')", Integer.class);
			if (oldColumns != null && oldColumns == 2) jdbc.execute("INSERT INTO turma_disciplina (turma_id, disciplina_id, professor_id) SELECT id, disciplina_id, professor_id FROM turma WHERE disciplina_id IS NOT NULL AND professor_id IS NOT NULL ON CONFLICT (turma_id, disciplina_id) DO NOTHING");
			jdbc.execute("ALTER TABLE IF EXISTS turma DROP COLUMN IF EXISTS disciplina_id CASCADE");
			jdbc.execute("ALTER TABLE IF EXISTS turma DROP COLUMN IF EXISTS professor_id CASCADE");
			jdbc.execute("ALTER TABLE IF EXISTS matricula ADD COLUMN IF NOT EXISTS turma_disciplina_id BIGINT");
			jdbc.execute("ALTER TABLE IF EXISTS matricula DROP CONSTRAINT IF EXISTS fk_matricula_grade");
			jdbc.execute("ALTER TABLE IF EXISTS matricula ADD CONSTRAINT fk_matricula_grade FOREIGN KEY (turma_disciplina_id) REFERENCES turma_disciplina (id)");
			jdbc.execute("ALTER TABLE IF EXISTS matricula DROP CONSTRAINT IF EXISTS matricula_aluno_id_turma_id_key");
			jdbc.execute("UPDATE matricula m SET turma_disciplina_id = td.id FROM turma_disciplina td WHERE m.turma_disciplina_id IS NULL AND m.turma_id = td.turma_id AND (SELECT COUNT(*) FROM turma_disciplina td2 WHERE td2.turma_id = m.turma_id) = 1");
			jdbc.execute("ALTER TABLE IF EXISTS matricula DROP CONSTRAINT IF EXISTS uk_matricula_aluno_grade");
			jdbc.execute("ALTER TABLE IF EXISTS nota ADD COLUMN IF NOT EXISTS turma_disciplina_id BIGINT");
			jdbc.execute("UPDATE nota n SET turma_disciplina_id = m.turma_disciplina_id FROM matricula m WHERE n.matricula_id = m.id AND n.turma_disciplina_id IS NULL AND m.turma_disciplina_id IS NOT NULL");
			jdbc.execute("ALTER TABLE IF EXISTS nota DROP CONSTRAINT IF EXISTS fk_nota_grade");
			jdbc.execute("ALTER TABLE IF EXISTS nota ADD CONSTRAINT fk_nota_grade FOREIGN KEY (turma_disciplina_id) REFERENCES turma_disciplina (id)");
			jdbc.execute("ALTER TABLE IF EXISTS matricula DROP CONSTRAINT IF EXISTS uk_matricula_aluno_turma");
			jdbc.execute("ALTER TABLE IF EXISTS matricula ADD CONSTRAINT uk_matricula_aluno_turma UNIQUE (aluno_id, turma_id)");
			jdbc.execute("ALTER TABLE IF EXISTS turma ADD COLUMN IF NOT EXISTS turno VARCHAR(30)");
		};
	}
}