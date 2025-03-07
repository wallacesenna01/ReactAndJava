package com.wallace.artur.demo.infra.repository;

import com.wallace.artur.demo.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
}
