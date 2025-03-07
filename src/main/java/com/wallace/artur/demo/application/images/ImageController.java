package com.wallace.artur.demo.application.images;

import com.wallace.artur.demo.domain.entity.Image;
import com.wallace.artur.demo.domain.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{id}")
    public ResponseEntity<byte []> getImage(@PathVariable String id) {
       var possibleImage =  service.getById(id);
       if(possibleImage.isEmpty()){
           return ResponseEntity.notFound().build();
       }

        var image =  possibleImage.get();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(image.getExtension().getMediaType());
        headers.setContentLength(image.getSize());
        //inline; filename="image.PNG"
        headers.setContentDispositionFormData("inline; filename=\"" + image.getFileName()+ "\"", image.getFileName());

          return new  ResponseEntity<>(image.getFile(),headers, HttpStatus.OK);
    }

    public URI buildImageUrl(Image image) {
        String imagePath = "/" + image.getId();
      return   ServletUriComponentsBuilder.fromCurrentRequest().path(imagePath).build().toUri();
    }

}
