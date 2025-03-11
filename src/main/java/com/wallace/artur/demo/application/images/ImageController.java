package com.wallace.artur.demo.application.images;

import com.wallace.artur.demo.domain.entity.Image;
import com.wallace.artur.demo.domain.enums.ImageExtension;
import com.wallace.artur.demo.domain.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

        return service.getById(id)
                .map(image -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(image.getExtension().getMediaType());
                    headers.setContentLength(image.getSize());
                    headers.setContentDisposition(ContentDisposition.inline()
                            .filename(image.getFileName()).build());
                    return new ResponseEntity<>(image.getFile(),headers, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //localhost:8080/v1/images/?extension=PNG&query
    @GetMapping
    public ResponseEntity<List<ImageDTO>> search(@RequestParam(value = "extension", required = false, defaultValue = "") String extension,
                                                 @RequestParam(value = "query", required = false) String query) {

        System.out.println("extension: " + extension);
      var result =  service.search(ImageExtension.valueOf(extension), query);

      var images =  result.stream().map(image -> {
          var url = buildImageUrl(image);
          return  mapper.imageDTO(image, url.toString());
    }).collect(Collectors.toList());
           return ResponseEntity.ok(images);
    }

    public URI buildImageUrl(Image image) {
        String imagePath = "/" + image.getId();
      return   ServletUriComponentsBuilder.fromCurrentRequest().path(imagePath).build().toUri();
    }

}
