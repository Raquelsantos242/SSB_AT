package com.br.infnet.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.br.infnet.model.Nota;

public interface NotaRepository extends JpaRepository<Nota, Long> {
    List<Nota> findByMatriculaId(Long matriculaId);
}
