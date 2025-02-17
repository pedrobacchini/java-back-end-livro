package com.santana.java.back.end.service;

import com.santana.java.back.end.converter.DTOConverter;
import com.santana.java.back.end.dto.RegisterUser;
import com.santana.java.back.end.dto.UserDTO;
import com.santana.java.back.end.exception.UserNotFoundException;
import com.santana.java.back.end.model.User;
import com.santana.java.back.end.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(DTOConverter::convert).collect(Collectors.toList());
    }

    public UserDTO findById(long userId) {
        Optional<User> users = userRepository.findById(userId);
        if (users.isPresent()) {
            return DTOConverter.convert(users.get());
        }
        throw new UserNotFoundException();
    }

    public UserDTO save(RegisterUser registerUser) {

        registerUser.setKey(UUID.randomUUID().toString());

        User user = userRepository.save(User.convert(registerUser));
        return DTOConverter.convert(user);
    }

    public UserDTO delete(long userId) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
        return DTOConverter.convert(user);
    }

    public UserDTO findByCpf(String cpf, String key) {
        User user = userRepository.findByCpfAndKey(cpf, key);
        if (user != null) {
            return DTOConverter.convert(user);
        }
        throw new UserNotFoundException();
    }

    public List<UserDTO> queryByName(String name) {
        List<User> users = userRepository.queryByNameLike(name);
        return users.stream().map(DTOConverter::convert).collect(Collectors.toList());
    }

}
