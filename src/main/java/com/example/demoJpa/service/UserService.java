package com.example.demoJpa.service;

import com.example.demoJpa.controller.dto.users.UserDTO;
import com.example.demoJpa.controller.mapper.UserMapper;
import com.example.demoJpa.domain.Users;
import com.example.demoJpa.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService extends GenericService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public Optional<Users> getByLogin(String username) {

        return usersRepository.findUsersByUsername(username);
    }

    public Optional<Users> getByEmail(String email) {

        return usersRepository.findUsersByEmail(email);
    }

    public Users persist(Users user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    public Integer saveUser(UserDTO userDTO) {

        Users user = userMapper.toUser(userDTO);
        return persist(user).getId();
    }

    @Transactional
    public void changeUserProperties(UserDTO userDTO) {

        User userAuth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = getByLogin(userAuth.getUsername()).get();
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setName(userDTO.getName());
        user.setRoleUser(userDTO.getRoleUser());
    }

    public void deleteUser(Integer id) {

        if (!usersRepository.existsById(id))
            throw new EntityNotFoundException("Id não encontrado no banco de dados");
        usersRepository.deleteById(id);
    }
}
