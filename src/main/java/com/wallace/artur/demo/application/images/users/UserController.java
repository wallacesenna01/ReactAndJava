package com.wallace.artur.demo.application.images.users;

import com.wallace.artur.demo.domain.entity.User;
import com.wallace.artur.demo.domain.exception.DuplicatedTupleException;
import com.wallace.artur.demo.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;

    @PostMapping
    public ResponseEntity save(@RequestBody UserDTO dto) {
        try {
            User user = mapper.mapToUser(dto);
            userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (DuplicatedTupleException e) {
            Map<String, String > jsonResult = Map.of("error", e.getMessage());
           return ResponseEntity.status(HttpStatus.CONFLICT).body(jsonResult);
        }

    }

    @PostMapping("/auth")
    public ResponseEntity authenticate(@RequestBody CredentialsDTO credentialsDTO) {
        var token = userService.authenticate(credentialsDTO.getEmail(), credentialsDTO.getPassword());
        if (token == null ) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }
        return ResponseEntity.ok(token);
    }
}
