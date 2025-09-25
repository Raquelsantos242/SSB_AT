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

import com.br.infnet.service.NotaService;

@WebMvcTest(controllers = NotaController.class)
class NotaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotaService notaService;

    @Test
    void testAtribuirNotaSemAutenticacao() throws Exception {
        mockMvc.perform(post("/notas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"matriculaId\":1,\"valor\":9.5}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void testListarPorMatriculaAutenticadoSemPermissao() throws Exception {
        mockMvc.perform(get("/notas/matricula/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testListarTodasSemAutenticacao() throws Exception {
        mockMvc.perform(get("/notas"))
                .andExpect(status().isUnauthorized());
    }

}
