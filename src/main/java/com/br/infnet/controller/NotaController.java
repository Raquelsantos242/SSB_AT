package com.br.infnet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.br.infnet.model.Nota;
import com.br.infnet.service.NotaService;

@RestController
@RequestMapping("/notas")
public class NotaController {
    private final NotaService notaService;

    public NotaController(NotaService notaService) {
        this.notaService = notaService;
    }

    @PostMapping
    public Nota atribuirNota(@RequestBody Nota nota) {
        return notaService.atribuirNota(nota);
    }

    @GetMapping("/matricula/{matriculaId}")
    public List<Nota> listarPorMatricula(@PathVariable Long matriculaId) {
        return notaService.listarPorMatricula(matriculaId);
    }

    @GetMapping
    public List<Nota> listarTodas() {
        return notaService.listarTodas();
    }

    @PutMapping("/{id}")
    public Nota atualizar(@PathVariable Long id, @RequestBody Nota notaAtualizada) {
        return notaService.atualizar(id, notaAtualizada);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        notaService.deletar(id);
    }
}
