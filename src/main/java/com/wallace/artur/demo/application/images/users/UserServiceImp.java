package com.wallace.artur.demo.application.images.users;

import com.wallace.artur.demo.application.images.jwt.JwtServices;
import com.wallace.artur.demo.domain.AccessToken;
import com.wallace.artur.demo.domain.entity.User;
import com.wallace.artur.demo.domain.exception.DuplicatedTupleException;
import com.wallace.artur.demo.domain.service.UserService;
import com.wallace.artur.demo.infra.repository.specs.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private  final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtServices jwtServices;


    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public User save(User user) {
        if(userRepository.findByEmail(user.getEmail()) != null) {
            throw new DuplicatedTupleException("User Already exists");
        }
        EncodePassword(user);
        return userRepository.save(user);
    }

    @Override
    public AccessToken authenticate(String email, String password) {
        var user =  getByEmail(email);

        if(user == null) {
            return null;
        }

        boolean matches = passwordEncoder.matches(password, user.getPassword());

        if(matches) {
            return jwtServices.genereteToken(user);
        }
        return null;
     }

    private void EncodePassword(User user ) {
        String password = user.getPassword();
        String encodePassword = passwordEncoder.encode(password);
        user.setPassword(encodePassword);
    }
}
