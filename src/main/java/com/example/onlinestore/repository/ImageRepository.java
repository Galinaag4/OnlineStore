package com.example.onlinestore.repository;

import com.example.onlinestore.model.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ImageRepository extends JpaRepository<ImageModel,Integer> {

}
