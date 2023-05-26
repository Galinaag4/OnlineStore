package com.example.onlinestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.onlinestore.model.AdsModel;
@Repository
public interface AdsRepository extends JpaRepository <AdsModel, Long> {
}
