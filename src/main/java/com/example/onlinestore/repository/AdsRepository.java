package com.example.onlinestore.repository;

import com.example.onlinestore.dto.Ads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.onlinestore.model.AdsModel;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdsRepository extends JpaRepository <AdsModel, Integer> {
    @Query(value = "select * from ads order by id", nativeQuery = true)
    List<AdsModel> findAllAds();
    List<AdsModel> findAllByUserModelId(Integer userModelId);

    List<AdsModel> findByTitleContainingIgnoreCaseOrderByTitle(String title);

}
