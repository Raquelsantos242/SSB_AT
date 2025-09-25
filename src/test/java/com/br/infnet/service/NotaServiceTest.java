package com.br.infnet.service;

import com.br.infnet.model.Nota;
import com.br.infnet.repository.NotaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class NotaServiceTest {

    private NotaRepository notaRepository;
    private NotaService notaService;

    @BeforeEach
    void setUp() {
        notaRepository = mock(NotaRepository.class);
        notaService = new NotaService(notaRepository);
    }

    @Test
    void testAtribuirNota() {
        Nota nota = new Nota();
        nota.setMatriculaId(1L);
        nota.setValor(8.0);

        when(notaRepository.save(nota)).thenReturn(nota);

        Nota resultado = notaService.atribuirNota(nota);
        assertThat(resultado).isEqualTo(nota);
        verify(notaRepository, times(1)).save(nota);
    }

    @Test
    void testListarPorMatricula() {
        Nota nota1 = new Nota();
        nota1.setMatriculaId(1L);
        Nota nota2 = new Nota();
        nota2.setMatriculaId(1L);

        when(notaRepository.findByMatriculaId(1L)).thenReturn(List.of(nota1, nota2));

        List<Nota> resultado = notaService.listarPorMatricula(1L);
        assertThat(resultado).hasSize(2);
    }

    @Test
    void testRelatorioMediaPorMatricula() {
        // Cenário de relatório: média das notas
        Nota nota1 = new Nota();
        nota1.setValor(8.0);
        Nota nota2 = new Nota();
        nota2.setValor(10.0);

        when(notaRepository.findByMatriculaId(1L)).thenReturn(List.of(nota1, nota2));

        List<Nota> notas = notaService.listarPorMatricula(1L);
        double media = notas.stream().mapToDouble(Nota::getValor).average().orElse(0);

        assertThat(media).isEqualTo(9.0);
    }
}
