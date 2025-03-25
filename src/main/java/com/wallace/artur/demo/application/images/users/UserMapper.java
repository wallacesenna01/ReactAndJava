package com.wallace.artur.demo.application.images.users;

import com.wallace.artur.demo.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public  User mapToUser(UserDTO dto) {
        return User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(dto.getPassword()).build();
    }
}
