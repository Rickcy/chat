package com.demo.chat.domain.service;

import com.demo.chat.infrastructure.repository.ClientRepository;
import com.demo.chat.utils.SecurityUser;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService implements UserDetailsService {


    private final ClientRepository clientRepository;

    public UserService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return clientRepository.findByName(username).stream()
                .filter(Objects::nonNull)
                .map(SecurityUser::new)
                .peek(user -> new AccountStatusUserDetailsChecker().check(user))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }


}
