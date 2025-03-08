package com.wallace.artur.demo.infra.repository;

import com.wallace.artur.demo.domain.entity.Image;
import com.wallace.artur.demo.domain.enums.ImageExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {
    default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query){
        return findAll();
    }
}
