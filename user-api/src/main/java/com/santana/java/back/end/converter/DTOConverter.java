package com.santana.java.back.end.converter;

import com.santana.java.back.end.dto.UserDTO;
import com.santana.java.back.end.model.User;

public class DTOConverter {

    public static UserDTO convert(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setAddress(user.getAddress());
        userDTO.setCpf(user.getCpf());
        userDTO.setKey(user.getKey());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setRegisterDate(user.getRegisterDate());
        return userDTO;
    }

}
