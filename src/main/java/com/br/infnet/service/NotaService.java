package com.br.infnet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.br.infnet.model.Nota;
import com.br.infnet.repository.NotaRepository;

@Service
public class NotaService {
    private final NotaRepository notaRepository;

    public NotaService(NotaRepository notaRepository) {
        this.notaRepository = notaRepository;
    }

    public Nota atribuirNota(Nota nota) {
        return notaRepository.save(nota);
    }

    public List<Nota> listarPorMatricula(Long matriculaId) {
        return notaRepository.findByMatriculaId(matriculaId);
    }

    public List<Nota> listarTodas() {
        return notaRepository.findAll();
    }

    public Nota atualizar(Long id, Nota notaAtualizada) {
        return notaRepository.findById(id)
                .map(nota -> {
                    nota.setValor(notaAtualizada.getValor());
                    nota.setMatriculaId(notaAtualizada.getMatriculaId());
                    return notaRepository.save(nota);
                }).orElseThrow(() -> new RuntimeException("Nota não encontrada"));
    }

    public void deletar(Long id) {
        notaRepository.deleteById(id);
    }
}
