package com.wallace.artur.demo.application.images;

import com.wallace.artur.demo.domain.entity.Image;
import com.wallace.artur.demo.domain.enums.ImageExtension;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class ImageMapper {

    public Image mapToImage(MultipartFile file, String name, List<String> tags) throws IOException {
        return  Image.builder()
                .name(name)
                .tags(String.join(",", tags))
                .size(file.getSize())
                .extension(ImageExtension.valueOf(MediaType.valueOf(Objects.requireNonNull(file.getContentType()))))
                .file(file.getBytes())
                .build();
    }
}

