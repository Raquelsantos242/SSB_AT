package com.br.infnet.service;

import com.br.infnet.model.Matricula;
import com.br.infnet.repository.MatriculaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MatriculaServiceTest {

    private MatriculaRepository matriculaRepository;
    private MatriculaService matriculaService;

    @BeforeEach
    void setUp() {
        matriculaRepository = Mockito.mock(MatriculaRepository.class);
        matriculaService = new MatriculaService(matriculaRepository);
    }

    @Test
    void testMatricular() {
        Matricula m = new Matricula();
        m.setAlunoId(1L);
        m.setDisciplinaId(1L);

        when(matriculaRepository.save(m)).thenReturn(m);

        Matricula resultado = matriculaService.matricular(m);
        assertThat(resultado).isEqualTo(m);
        verify(matriculaRepository, times(1)).save(m);
    }

    @Test
    void testListarPorAluno() {
        Matricula m = new Matricula();
        m.setAlunoId(1L);

        when(matriculaRepository.findByAlunoId(1L)).thenReturn(List.of(m));

        List<Matricula> resultado = matriculaService.listarPorAluno(1L);
        assertThat(resultado).hasSize(1).contains(m);
    }

    @Test
    void testAtualizar() {
        Matricula original = new Matricula();
        original.setId(1L);
        original.setAlunoId(1L);
        original.setDisciplinaId(1L);

        Matricula atualizada = new Matricula();
        atualizada.setAlunoId(2L);
        atualizada.setDisciplinaId(3L);

        when(matriculaRepository.findById(1L)).thenReturn(Optional.of(original));
        when(matriculaRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Matricula resultado = matriculaService.atualizar(1L, atualizada);
        assertThat(resultado.getAlunoId()).isEqualTo(2L);
        assertThat(resultado.getDisciplinaId()).isEqualTo(3L);
    }
}
