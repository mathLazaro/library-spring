package com.example.demoJpa.service;

import com.example.demoJpa.controller.dto.users.UserDTO;
import com.example.demoJpa.controller.mapper.UserMapper;
import com.example.demoJpa.domain.Users;
import com.example.demoJpa.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public Optional<Users> getByLogin(String username) {
        return usersRepository.findUsersByUsername(username);
    }

    public Integer saveUser(UserDTO userDTO) {
        Users user = userMapper.toUser(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return usersRepository.save(user).getId();
    }
}
