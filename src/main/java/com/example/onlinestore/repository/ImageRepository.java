package com.example.onlinestore.repository;

import com.example.onlinestore.model.ImageModel;
import com.example.onlinestore.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ImageRepository extends JpaRepository<ImageModel,Integer> {


    void deleteImageModelsByAdsModel_Id(Integer id);
}
