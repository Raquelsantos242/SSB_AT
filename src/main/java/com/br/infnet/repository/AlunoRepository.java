package com.br.infnet.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.br.infnet.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByCpf(String cpf);
}
