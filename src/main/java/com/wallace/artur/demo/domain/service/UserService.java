package com.wallace.artur.demo.domain.service;

import com.wallace.artur.demo.domain.AccessToken;
import com.wallace.artur.demo.domain.entity.User;

public interface UserService {
    User getByEmail(String email);
    User save (User user);

    AccessToken authenticate(String email, String password);
}
