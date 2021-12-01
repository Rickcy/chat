package com.demo.chat.infrastructure.config;

import com.demo.chat.domain.entity.Client;
import com.demo.chat.infrastructure.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class AppRunner {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;


    @Bean
    public CommandLineRunner run() {

        clientRepository.save(Client.builder().name("client1").password(passwordEncoder.encode("client1")).build());
        clientRepository.save(Client.builder().name("client2").password(passwordEncoder.encode("client2")).build());

        return args -> {
            log.debug("Complete!");
        };
    }


}
