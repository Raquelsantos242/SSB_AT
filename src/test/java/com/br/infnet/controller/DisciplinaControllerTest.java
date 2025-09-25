package com.br.infnet.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DisciplinaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String jwt;

    @BeforeEach
    void setUp() throws Exception {
        jwt = authenticateAndGetJwt("professor2", "senha123");
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
    void criarDisciplina_sucesso() throws Exception {
        Map<String, String> disciplina = Map.of(
                "nome", "Matemática",
                "codigo", "MAT101"
        );

        mockMvc.perform(post("/disciplinas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(disciplina))
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Matemática"))
                .andExpect(jsonPath("$.codigo").value("MAT101"));
    }

    @Test
    void listarDisciplinas_sucesso() throws Exception {
        mockMvc.perform(get("/disciplinas")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void buscarDisciplinaPorId_sucesso() throws Exception {
        Map<String, String> disciplina = Map.of("nome", "Física", "codigo", "FIS101");
        MvcResult result = mockMvc.perform(post("/disciplinas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(disciplina))
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        Long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(get("/disciplinas/" + id)
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Física"));
    }

    @Test
    void atualizarDisciplina_sucesso() throws Exception {
        Map<String, String> disciplina = Map.of("nome", "Química", "codigo", "QUI101");
        MvcResult result = mockMvc.perform(post("/disciplinas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(disciplina))
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andReturn();

        Long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();

        Map<String, String> disciplinaAtualizada = Map.of("nome", "Química Avançada", "codigo", "QUI201");
        mockMvc.perform(put("/disciplinas/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(disciplinaAtualizada))
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Química Avançada"))
                .andExpect(jsonPath("$.codigo").value("QUI201"));
    }

    @Test
    void deletarDisciplina_sucesso() throws Exception {
        Map<String, String> disciplina = Map.of("nome", "Biologia", "codigo", "BIO101");
        MvcResult result = mockMvc.perform(post("/disciplinas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(disciplina))
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andReturn();

        Long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();

        mockMvc.perform(delete("/disciplinas/" + id)
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk());
    }
}
