package com.wallace.artur.demo.infra.repository.specs;

import com.wallace.artur.demo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findByEmail(String email);
}
