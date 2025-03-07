package com.wallace.artur.demo.domain.service;

import com.wallace.artur.demo.domain.entity.Image;

import java.util.Optional;

public interface ImageService {
    Image save(Image image);
    Optional<Image> getById(String id);
}

