package com.br.infnet.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.br.infnet.model.Matricula;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    List<Matricula> findByAlunoId(Long alunoId);
    List<Matricula> findByDisciplinaId(Long disciplinaId);
    Optional<Matricula> findByAlunoIdAndDisciplinaId(Long alunoId, Long disciplinaId);
}
