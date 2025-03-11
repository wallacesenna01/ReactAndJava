package com.wallace.artur.demo.infra.repository.specs;

import com.wallace.artur.demo.domain.entity.Image;
import com.wallace.artur.demo.domain.enums.ImageExtension;
import org.springframework.data.jpa.domain.Specification;

public class ImageSpecs {

    private ImageSpecs() {}

    public static Specification<Image> extensionEqual(ImageExtension extension) {
        return (root,q,cb) -> cb.equal(root.get("extension"), extension);
    }


    public static Specification nameLike(String name) {
        return (root,q,cb) ->
                cb.like(cb.upper(root.get("name")), "%" + name.toUpperCase()+ "%");
    }

    public static Specification tagsLike(String tags) {
        return (root,q,cb) ->
                cb.like(cb.upper(root.get("name")), "%" + tags.toUpperCase()+ "%");
    }
}
