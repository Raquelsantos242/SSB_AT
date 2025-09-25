package com.br.infnet.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.br.infnet.model.Disciplina;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    Optional<Disciplina> findByCodigo(String codigo);
}
