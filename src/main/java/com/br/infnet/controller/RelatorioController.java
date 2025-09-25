package com.br.infnet.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;

import com.br.infnet.model.Aluno;
import com.br.infnet.model.Matricula;
import com.br.infnet.model.Nota;
import com.br.infnet.service.AlunoService;
import com.br.infnet.service.MatriculaService;
import com.br.infnet.service.NotaService;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {
    private final MatriculaService matriculaService;
    private final NotaService notaService;
    private final AlunoService alunoService;

    public RelatorioController(MatriculaService matriculaService, NotaService notaService, AlunoService alunoService) {
        this.matriculaService = matriculaService;
        this.notaService = notaService;
        this.alunoService = alunoService;
    }

    @GetMapping("/disciplinas/{disciplinaId}/aprovados")
    public List<Aluno> aprovados(@PathVariable Long disciplinaId) {
        List<Matricula> matriculas = matriculaService.listarPorDisciplina(disciplinaId);
        List<Aluno> aprovados = new ArrayList<>();

        for (Matricula m : matriculas) {
            List<Nota> notas = notaService.listarPorMatricula(m.getId());
            Optional<Nota> best = notas.stream().max(Comparator.comparingDouble(Nota::getValor));
            if (best.isPresent() && best.get().getValor() >= 7.0) {
                alunoService.buscarPorId(m.getAlunoId()).ifPresent(aprovados::add);
            }
        }

        return aprovados.stream().distinct().collect(Collectors.toList());
    }

    @GetMapping("/disciplinas/{disciplinaId}/reprovados")
    public List<Aluno> reprovados(@PathVariable Long disciplinaId) {
        List<Matricula> matriculas = matriculaService.listarPorDisciplina(disciplinaId);
        List<Aluno> reprovados = new ArrayList<>();

        for (Matricula m : matriculas) {
            List<Nota> notas = notaService.listarPorMatricula(m.getId());
            Optional<Nota> best = notas.stream().max(Comparator.comparingDouble(Nota::getValor));
            if (best.isPresent()) {
                if (best.get().getValor() < 7.0) {
                    alunoService.buscarPorId(m.getAlunoId()).ifPresent(reprovados::add);
                }
            } else {
                alunoService.buscarPorId(m.getAlunoId()).ifPresent(reprovados::add);
            }
        }

        return reprovados.stream().distinct().collect(Collectors.toList());
    }
}
