package com.example.onlinestore.repository;

import com.example.onlinestore.dto.Ads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.onlinestore.model.AdsModel;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdsRepository extends JpaRepository <AdsModel, Long> {
    Collection<AdsModel> findByTitleContainsOrderByTitle(String title);
    Integer getUserModelId(Integer adsId);
    void deleteAllById(Integer adsId);
    Collection<AdsModel> findByAuthorId(int authorId);

}
