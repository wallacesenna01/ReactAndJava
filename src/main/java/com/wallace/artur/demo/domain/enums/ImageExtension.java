package com.wallace.artur.demo.domain.enums;

import lombok.Getter;
import org.springframework.http.MediaType;

import java.util.Arrays;

@Getter
public enum ImageExtension {
    PNG(MediaType.IMAGE_PNG),
    JPEG(MediaType.IMAGE_JPEG),
    GIF(MediaType.IMAGE_GIF);

    private  MediaType mediaType;

    ImageExtension(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public static ImageExtension valueOf(MediaType mediaType) {
        return Arrays.stream(values()).
                filter(ie -> ie.mediaType.equals(mediaType)).
                findFirst().
                orElse(null);
    }
}
