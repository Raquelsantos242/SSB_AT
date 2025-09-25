package com.br.infnet.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.br.infnet.model.Aluno;
import com.br.infnet.repository.AlunoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    private Aluno aluno;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Maria");
        aluno.setCpf("12345678900");
        aluno.setEmail("maria@email.com");
        aluno.setTelefone("11999999999");
        aluno.setEndereco("Rua A, 100");
    }

    @Test
    void testCriar() {
        when(alunoRepository.save(aluno)).thenReturn(aluno);
        Aluno resultado = alunoService.criar(aluno);
        assertEquals(aluno.getNome(), resultado.getNome());
    }

    @Test
    void testListar() {
        when(alunoRepository.findAll()).thenReturn(List.of(aluno));
        List<Aluno> lista = alunoService.listar();
        assertEquals(1, lista.size());
    }

    @Test
    void testBuscarPorId() {
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        Optional<Aluno> resultado = alunoService.buscarPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals(aluno.getNome(), resultado.get().getNome());
    }

    @Test
    void testBuscarPorCpf() {
        when(alunoRepository.findByCpf("12345678900")).thenReturn(Optional.of(aluno));
        Optional<Aluno> resultado = alunoService.buscarPorCpf("12345678900");
        assertTrue(resultado.isPresent());
    }

    @Test
    void testAtualizar() {
        Aluno atualizado = new Aluno();
        atualizado.setNome("Rebecca");
        atualizado.setCpf("98765432100");
        atualizado.setEmail("rebecca@email.com");
        atualizado.setTelefone("11888888888");
        atualizado.setEndereco("Rua B, 200");

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(alunoRepository.save(any(Aluno.class))).thenReturn(atualizado);

        Aluno resultado = alunoService.atualizar(1L, atualizado);
        assertEquals("Rebecca", resultado.getNome());
        assertEquals("98765432100", resultado.getCpf());
    }

    @Test
    void testDeletar() {
        doNothing().when(alunoRepository).deleteById(1L);
        assertDoesNotThrow(() -> alunoService.deletar(1L));
        verify(alunoRepository, times(1)).deleteById(1L);
    }
}
