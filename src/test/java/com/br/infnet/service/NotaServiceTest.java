package com.br.infnet.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.br.infnet.model.Nota;
import com.br.infnet.repository.NotaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class NotaServiceTest {

    @Mock
    private NotaRepository notaRepository;

    @InjectMocks
    private NotaService notaService;

    private Nota nota;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        nota = new Nota();
        nota.setId(1L);
        nota.setMatriculaId(100L);
        nota.setValor(8.0);
    }

    @Test
    void testAtribuirNota() {
        when(notaRepository.save(nota)).thenReturn(nota);

        Nota resultado = notaService.atribuirNota(nota);
        assertEquals(8.0, resultado.getValor());
        verify(notaRepository, times(1)).save(nota);
    }

    @Test
    void testListarPorMatricula() {
        when(notaRepository.findByMatriculaId(100L)).thenReturn(List.of(nota));

        List<Nota> notas = notaService.listarPorMatricula(100L);
        assertFalse(notas.isEmpty());
        assertEquals(1, notas.size());
    }

    @Test
    void testListarTodas() {
        when(notaRepository.findAll()).thenReturn(List.of(nota));

        List<Nota> notas = notaService.listarTodas();
        assertEquals(1, notas.size());
    }

    @Test
    void testAtualizarSucesso() {
        Nota notaAtualizada = new Nota();
        notaAtualizada.setValor(9.0);
        notaAtualizada.setMatriculaId(100L);

        when(notaRepository.findById(1L)).thenReturn(Optional.of(nota));
        when(notaRepository.save(any(Nota.class))).thenReturn(nota);

        Nota resultado = notaService.atualizar(1L, notaAtualizada);
        assertEquals(9.0, resultado.getValor());
        verify(notaRepository).save(nota);
    }

    @Test
    void testAtualizarNaoEncontrada() {
        Nota notaAtualizada = new Nota();
        notaAtualizada.setValor(9.0);

        when(notaRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            notaService.atualizar(1L, notaAtualizada);
        });

        assertEquals("Nota não encontrada", exception.getMessage());
    }

    @Test
    void testDeletar() {
        doNothing().when(notaRepository).deleteById(1L);
        notaService.deletar(1L);
        verify(notaRepository, times(1)).deleteById(1L);
    }
}
