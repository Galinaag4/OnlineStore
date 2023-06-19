package com.example.onlinestore.repository;

import com.example.onlinestore.model.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageModel,Integer> {
}
