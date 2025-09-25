package com.br.infnet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.br.infnet.model.Disciplina;
import com.br.infnet.repository.DisciplinaRepository;

@Service
public class DisciplinaService {
    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    public Disciplina criar(Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }

    public List<Disciplina> listar() {
        return disciplinaRepository.findAll();
    }

    public Optional<Disciplina> buscarPorId(Long id) {
        return disciplinaRepository.findById(id);
    }

    public Optional<Disciplina> buscarPorCodigo(String codigo) {
        return disciplinaRepository.findByCodigo(codigo);
    }

    public Disciplina atualizar(Long id, Disciplina disciplinaAtualizada) {
        return disciplinaRepository.findById(id)
                .map(disciplina -> {
                    disciplina.setCodigo(disciplinaAtualizada.getCodigo());
                    disciplina.setNome(disciplinaAtualizada.getNome());
                    return disciplinaRepository.save(disciplina);
                }).orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));
    }

    public void deletar(Long id) {
        disciplinaRepository.deleteById(id);
    }
}
