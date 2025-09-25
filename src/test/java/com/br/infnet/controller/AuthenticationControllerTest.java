package com.br.infnet.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.br.infnet.service.AuthenticationService;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    void shouldAuthenticateAndReturnToken() {
        String expectedToken = "jwt-token-123";
        AuthenticationController.AuthRequest request =
                new AuthenticationController.AuthRequest("user", "pass");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authenticationService.authenticate(authentication)).thenReturn(expectedToken);

        String result = authenticationController.authenticate(request);

        assertEquals(expectedToken, result);
        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(authenticationService, times(1)).authenticate(authentication);
    }

    @Test
    void shouldThrowExceptionForInvalidCredentials() {
        AuthenticationController.AuthRequest request =
                new AuthenticationController.AuthRequest("user", "wrongpass");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Usuário ou senha inválidos"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authenticationController.authenticate(request));

        assertEquals("Usuário ou senha inválidos", exception.getMessage());
        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(authenticationService, never()).authenticate(any());
    }
}
