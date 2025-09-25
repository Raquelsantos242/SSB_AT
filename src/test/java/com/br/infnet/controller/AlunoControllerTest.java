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
public class AlunoControllerTest {

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
    void criarAluno_sucesso() throws Exception {
        Map<String, String> aluno = Map.of(
                "nome", "João",
                "cpf", "12345678900",
                "email", "joao@email.com",
                "telefone", "999999999",
                "endereco", "Rua A, 123"
        );

        mockMvc.perform(post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aluno))
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.cpf").value("12345678900"));
    }

    @Test
    void listarAlunos_sucesso() throws Exception {
        mockMvc.perform(get("/alunos")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void buscarAlunoPorId_sucesso() throws Exception {
        Map<String, String> aluno = Map.of(
                "nome", "Maria",
                "cpf", "98765432100",
                "email", "maria@email.com",
                "telefone", "888888888",
                "endereco", "Rua B, 456"
        );

        MvcResult result = mockMvc.perform(post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aluno))
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andReturn();

        Long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();

        mockMvc.perform(get("/alunos/" + id)
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Maria"));
    }

    @Test
    void atualizarAluno_sucesso() throws Exception {
        Map<String, String> aluno = Map.of(
                "nome", "Pedro",
                "cpf", "11122233344",
                "email", "pedro@email.com",
                "telefone", "777777777",
                "endereco", "Rua C, 789"
        );

        MvcResult result = mockMvc.perform(post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aluno))
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andReturn();

        Long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();

        Map<String, String> alunoAtualizado = Map.of(
                "nome", "Pedro Atualizado",
                "cpf", "11122233344",
                "email", "pedro@email.com",
                "telefone", "777777777",
                "endereco", "Rua C, 789"
        );

        mockMvc.perform(put("/alunos/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(alunoAtualizado))
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Pedro Atualizado"));
    }

    @Test
    void deletarAluno_sucesso() throws Exception {
        Map<String, String> aluno = Map.of(
                "nome", "Lucas",
                "cpf", "55566677788",
                "email", "lucas@email.com",
                "telefone", "666666666",
                "endereco", "Rua D, 101"
        );

        MvcResult result = mockMvc.perform(post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aluno))
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andReturn();

        Long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();

        mockMvc.perform(delete("/alunos/" + id)
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk());
    }
}
