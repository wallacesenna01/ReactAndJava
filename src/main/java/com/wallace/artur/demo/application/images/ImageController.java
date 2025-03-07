package com.wallace.artur.demo.application.images;

import com.wallace.artur.demo.domain.entity.Image;
import com.wallace.artur.demo.domain.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("v1/images")
@RequiredArgsConstructor

public class ImageController {

    private final ImageService service;
    private final ImageMapper mapper;

    private static final Logger log = LoggerFactory.getLogger(ImageController.class);

    @PostMapping
    public ResponseEntity save(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("tags") List<String> tags
            ) throws IOException {

        log.info("Imagem recebida: name: {}, size: {}",file.getName(),file.getSize());


        Image image = mapper.mapToImage(file,name,tags);
        Image savedImage =   service.save(image);

        URI imageUri = buildImageUrl(savedImage);

        return  ResponseEntity.created(imageUri).build();
    }

    public URI buildImageUrl(Image image) {
        String imagePath = "/" + image.getId();
      return   ServletUriComponentsBuilder.fromCurrentRequest().path(imagePath).build().toUri();
    }


}
