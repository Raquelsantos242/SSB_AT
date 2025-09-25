package com.br.infnet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.br.infnet.model.Aluno;
import com.br.infnet.repository.AlunoRepository;

@Service
public class AlunoService {
    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public Aluno criar(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public List<Aluno> listar() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarPorId(Long id) {
        return alunoRepository.findById(id);
    }

    public Optional<Aluno> buscarPorCpf(String cpf) {
        return alunoRepository.findByCpf(cpf);
    }

    public Aluno atualizar(Long id, Aluno alunoAtualizado) {
        return alunoRepository.findById(id)
                .map(aluno -> {
                    aluno.setNome(alunoAtualizado.getNome());
                    aluno.setCpf(alunoAtualizado.getCpf());
                    aluno.setEmail(alunoAtualizado.getEmail());
                    aluno.setTelefone(alunoAtualizado.getTelefone());
                    aluno.setEndereco(alunoAtualizado.getEndereco());
                    return alunoRepository.save(aluno);
                }).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }

    public void deletar(Long id) {
        alunoRepository.deleteById(id);
    }
}
