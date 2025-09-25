package com.br.infnet.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.br.infnet.model.User;
import com.br.infnet.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserService userService;

    public DataLoader(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("professor2").isEmpty()) {
            User defaultUser = new User();
            defaultUser.setUsername("professor2");
            defaultUser.setPassword("senha123");
            userService.criar(defaultUser);
            System.out.println("Usuário padrão criado: professor2/senha123");
        } else {
            System.out.println("Usuário professor2 já existe.");
        }
    }
}
