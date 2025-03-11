package com.wallace.artur.demo.infra.repository;

import com.wallace.artur.demo.domain.entity.Image;
import com.wallace.artur.demo.domain.enums.ImageExtension;
import com.wallace.artur.demo.infra.repository.specs.GenereicSpecs;
import com.wallace.artur.demo.infra.repository.specs.ImageSpecs;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.wallace.artur.demo.infra.repository.specs.GenereicSpecs.*;
import static com.wallace.artur.demo.infra.repository.specs.ImageSpecs.*;
import static org.springframework.data.jpa.domain.Specification.*;

public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {
    default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query){

        Specification<Image> spec = where(conjunction());

        // and extension = 'PNG'
        if(extension != null) {
            spec = spec.and(extensionEqual(extension));
        }
        if (StringUtils.hasText(query)){
            spec = spec.and(anyOf(nameLike(query), tagsLike(query)));
        }

        return findAll(spec);
    }
}
