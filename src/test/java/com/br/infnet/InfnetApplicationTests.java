package com.br.infnet;

import com.br.infnet.model.Aluno;
import com.br.infnet.model.User;
import com.br.infnet.repository.AlunoRepository;
import com.br.infnet.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InfnetApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    private String jwtToken;

    @BeforeEach
    void setup() {
        if (userRepository.findByUsername("professor2").isEmpty()) {
            User user = new User();
            user.setUsername("professor2");
            user.setPassword("senha123");
            userRepository.save(user);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = "{\"username\":\"professor2\",\"password\":\"senha123\"}";
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("/authenticate", request, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        jwtToken = response.getBody();
    }

    @Test
    void autenticarUsuario() {
        assertNotNull(jwtToken);
        assertTrue(jwtToken.length() > 10);
    }

    @Test
    void criarAluno() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String alunoJson = """
                {
                    "nome": "Fulano",
                    "cpf": "12345678900",
                    "email": "fulano@teste.com",
                    "telefone": "999999999",
                    "endereco": "Rua A, 123"
                }
                """;

        HttpEntity<String> request = new HttpEntity<>(alunoJson, headers);
        ResponseEntity<Aluno> response = restTemplate.postForEntity("/alunos", request, Aluno.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Fulano", response.getBody().getNome());
    }

    @Test
    void listarAlunos() {
        Aluno aluno = new Aluno();
        aluno.setNome("Maria");
        aluno.setCpf("98765432100");
        alunoRepository.save(aluno);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Aluno[]> response = restTemplate.exchange("/alunos", HttpMethod.GET, request, Aluno[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length >= 1);
    }
}
