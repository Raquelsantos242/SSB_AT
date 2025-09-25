package com.br.infnet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.br.infnet.model.Matricula;
import com.br.infnet.repository.MatriculaRepository;

@Service
public class MatriculaService {
    private final MatriculaRepository matriculaRepository;

    public MatriculaService(MatriculaRepository matriculaRepository) {
        this.matriculaRepository = matriculaRepository;
    }

    public Matricula matricular(Matricula matricula) {
        return matriculaRepository.save(matricula);
    }

    public List<Matricula> listarPorAluno(Long alunoId) {
        return matriculaRepository.findByAlunoId(alunoId);
    }

    public List<Matricula> listarPorDisciplina(Long disciplinaId) {
        return matriculaRepository.findByDisciplinaId(disciplinaId);
    }

    public Optional<Matricula> buscarPorAlunoEDisciplina(Long alunoId, Long disciplinaId) {
        return matriculaRepository.findByAlunoIdAndDisciplinaId(alunoId, disciplinaId);
    }

    public Matricula atualizar(Long id, Matricula matriculaAtualizada) {
        return matriculaRepository.findById(id)
                .map(matricula -> {
                    matricula.setAlunoId(matriculaAtualizada.getAlunoId());
                    matricula.setDisciplinaId(matriculaAtualizada.getDisciplinaId());
                    return matriculaRepository.save(matricula);
                }).orElseThrow(() -> new RuntimeException("Matrícula não encontrada"));
    }

    public void deletar(Long id) {
        matriculaRepository.deleteById(id);
    }
}
