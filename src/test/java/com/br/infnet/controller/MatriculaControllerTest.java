package com.br.infnet.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.br.infnet.service.MatriculaService;

@WebMvcTest(controllers = MatriculaController.class)
class MatriculaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatriculaService matriculaService;

    @Test
    void testMatricularSemAutenticacao() throws Exception {
        mockMvc.perform(post("/matriculas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"alunoId\":1,\"disciplinaId\":1}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void testListarPorAlunoAutenticadoSemPermissao() throws Exception {
        mockMvc.perform(get("/matriculas/aluno/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testListarPorDisciplinaAutenticadoSemPermissao() throws Exception {
        mockMvc.perform(get("/matriculas/disciplina/1"))
                .andExpect(status().isOk());
    }
}
