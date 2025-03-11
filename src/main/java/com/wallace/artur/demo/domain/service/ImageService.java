package com.wallace.artur.demo.domain.service;

import com.wallace.artur.demo.domain.entity.Image;
import com.wallace.artur.demo.domain.enums.ImageExtension;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    Image save(Image image);
    Optional<Image> getById(String id);

    List<Image> search(ImageExtension extension, String query);
}

