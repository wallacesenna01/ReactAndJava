package com.wallace.artur.demo.application.images;

import com.wallace.artur.demo.domain.entity.Image;
import com.wallace.artur.demo.domain.service.ImageService;
import com.wallace.artur.demo.infra.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ImageServiceImp implements ImageService {

    private final ImageRepository ImageRepository;

    @Override
    @Transactional
    public Image save(Image image) {
        return ImageRepository.save(image);
    }
}
