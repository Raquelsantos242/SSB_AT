package com.br.infnet.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.br.infnet.model.Disciplina;
import com.br.infnet.repository.DisciplinaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class DisciplinaServiceTest {

    @Mock
    private DisciplinaRepository disciplinaRepository;

    @InjectMocks
    private DisciplinaService disciplinaService;

    private Disciplina disciplina;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        disciplina = new Disciplina();
        disciplina.setId(1L);
        disciplina.setNome("Matemática");
        disciplina.setCodigo("MAT101");
    }

    @Test
    void testCriar() {
        when(disciplinaRepository.save(disciplina)).thenReturn(disciplina);
        Disciplina resultado = disciplinaService.criar(disciplina);
        assertEquals("Matemática", resultado.getNome());
    }

    @Test
    void testListar() {
        when(disciplinaRepository.findAll()).thenReturn(List.of(disciplina));
        List<Disciplina> lista = disciplinaService.listar();
        assertEquals(1, lista.size());
    }

    @Test
    void testBuscarPorId() {
        when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(disciplina));
        Optional<Disciplina> resultado = disciplinaService.buscarPorId(1L);
        assertTrue(resultado.isPresent());
    }

    @Test
    void testBuscarPorCodigo() {
        when(disciplinaRepository.findByCodigo("MAT101")).thenReturn(Optional.of(disciplina));
        Optional<Disciplina> resultado = disciplinaService.buscarPorCodigo("MAT101");
        assertTrue(resultado.isPresent());
    }

    @Test
    void testAtualizar() {
        Disciplina atualizado = new Disciplina();
        atualizado.setNome("Física");
        atualizado.setCodigo("FIS101");

        when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(disciplina));
        when(disciplinaRepository.save(any(Disciplina.class))).thenReturn(atualizado);

        Disciplina resultado = disciplinaService.atualizar(1L, atualizado);
        assertEquals("Física", resultado.getNome());
        assertEquals("FIS101", resultado.getCodigo());
    }

    @Test
    void testDeletar() {
        doNothing().when(disciplinaRepository).deleteById(1L);
        assertDoesNotThrow(() -> disciplinaService.deletar(1L));
        verify(disciplinaRepository, times(1)).deleteById(1L);
    }
}
