package com.br.infnet.service;

import com.br.infnet.model.User;
import com.br.infnet.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void testCriarUsuarioExistente() {
        User user = new User();
        user.setUsername("admin");

        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> userService.criar(user))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Usuário já existe");
    }

    @Test
    void testCriarUsuarioComSucesso() {
        User user = new User();
        user.setUsername("novo");
        user.setPassword("123");

        when(userRepository.findByUsername("novo")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123")).thenReturn("encoded123");
        when(userRepository.save(user)).thenReturn(user);

        userService.criar(user);
        verify(userRepository, times(1)).save(user);
        assert(user.getPassword().equals("encoded123"));
    }
}
