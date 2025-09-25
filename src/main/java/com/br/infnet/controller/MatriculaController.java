package com.br.infnet.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

import com.br.infnet.model.Matricula;
import com.br.infnet.service.MatriculaService;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {
    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @PostMapping
    public Matricula matricular(@RequestBody Matricula matricula) {
        return matriculaService.matricular(matricula);
    }

    @GetMapping("/aluno/{alunoId}")
    public List<Matricula> listarPorAluno(@PathVariable Long alunoId) {
        return matriculaService.listarPorAluno(alunoId);
    }

    @GetMapping("/disciplina/{disciplinaId}")
    public List<Matricula> listarPorDisciplina(@PathVariable Long disciplinaId) {
        return matriculaService.listarPorDisciplina(disciplinaId);
    }

    @GetMapping("/aluno/{alunoId}/disciplina/{disciplinaId}")
    public Optional<Matricula> buscarPorAlunoEDisciplina(@PathVariable Long alunoId, @PathVariable Long disciplinaId) {
        return matriculaService.buscarPorAlunoEDisciplina(alunoId, disciplinaId);
    }

    @PutMapping("/{id}")
    public Matricula atualizar(@PathVariable Long id, @RequestBody Matricula matriculaAtualizada) {
        return matriculaService.atualizar(id, matriculaAtualizada);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        matriculaService.deletar(id);
    }
}
