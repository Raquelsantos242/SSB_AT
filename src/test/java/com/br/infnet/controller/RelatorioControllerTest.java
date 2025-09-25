package com.br.infnet.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.br.infnet.model.Aluno;
import com.br.infnet.model.Matricula;
import com.br.infnet.model.Nota;
import com.br.infnet.repository.AlunoRepository;
import com.br.infnet.repository.MatriculaRepository;
import com.br.infnet.repository.NotaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RelatorioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private NotaRepository notaRepository;

    private String jwt;

    @BeforeEach
    void setUp() throws Exception {
        jwt = authenticateAndGetJwt("professor2", "senha123");
        alunoRepository.deleteAll();
        matriculaRepository.deleteAll();
        notaRepository.deleteAll();
    }

    private String authenticateAndGetJwt(String username, String password) throws Exception {
        Map<String, String> body = Map.of("username", username, "password", password);
        MvcResult result = mockMvc.perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andReturn();
        return result.getResponse().getContentAsString();
    }

    @Test
    void aprovadosEReprovados_sucesso() throws Exception {

        Aluno aluno = new Aluno();
        aluno.setNome("João");
        aluno.setCpf("12345678900");
        aluno.setEmail("joao@email.com");
        alunoRepository.save(aluno);

        Matricula matricula = new Matricula();
        matricula.setAlunoId(aluno.getId());
        matricula.setDisciplinaId(1L);
        matriculaRepository.save(matricula);

        Nota nota = new Nota();
        nota.setMatriculaId(matricula.getId());
        nota.setValor(8.0);
        notaRepository.save(nota);

        mockMvc.perform(get("/relatorios/disciplinas/1/aprovados")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João"));

        mockMvc.perform(get("/relatorios/disciplinas/1/reprovados")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
