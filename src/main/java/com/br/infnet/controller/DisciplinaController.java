package com.br.infnet.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

import com.br.infnet.model.Disciplina;
import com.br.infnet.service.DisciplinaService;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {
    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @PostMapping
    public Disciplina criar(@RequestBody Disciplina disciplina) {
        return disciplinaService.criar(disciplina);
    }

    @GetMapping
    public List<Disciplina> listar() {
        return disciplinaService.listar();
    }

    @GetMapping("/{id}")
    public Optional<Disciplina> buscarPorId(@PathVariable Long id) {
        return disciplinaService.buscarPorId(id);
    }

    @GetMapping("/codigo/{codigo}")
    public Optional<Disciplina> buscarPorCodigo(@PathVariable String codigo) {
        return disciplinaService.buscarPorCodigo(codigo);
    }

    @PutMapping("/{id}")
    public Disciplina atualizar(@PathVariable Long id, @RequestBody Disciplina disciplinaAtualizada) {
        return disciplinaService.atualizar(id, disciplinaAtualizada);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        disciplinaService.deletar(id);
    }
}
