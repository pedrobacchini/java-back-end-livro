package com.santana.java.back.end.controller;

import com.santana.java.back.end.dto.RegisterUser;
import com.santana.java.back.end.dto.UserDTO;
import com.santana.java.back.end.exception.UserNotFoundException;
import com.santana.java.back.end.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public List<UserDTO> getUsers() {
        return userService.getAll();
    }

    @GetMapping("/user/{id}")
    public UserDTO findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping("/user")
    public UserDTO newUser(@RequestBody RegisterUser registerUser) {
        return userService.save(registerUser);
    }

    @GetMapping("/user/cpf/{cpf}")
    public UserDTO findByCpf(
            @RequestParam(name = "key") String key,
            @PathVariable String cpf) {
        return userService.findByCpf(cpf, key);
    }

    @DeleteMapping("/user/{id}")
    public UserDTO delete(@PathVariable Long id) throws UserNotFoundException {
        return userService.delete(id);
    }

    @GetMapping("/user/search")
    public List<UserDTO> queryByName(
            @RequestParam(name = "name") String name) {
        return userService.queryByName(name);
    }

}
