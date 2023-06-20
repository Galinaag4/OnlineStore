package com.example.onlinestore.repository;

import com.example.onlinestore.dto.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;
import com.example.onlinestore.model.CommentModel;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository <CommentModel, Long> {
    Integer getUserProfileId(int adsId, int commentId);

    void deleteByAdsIdAndId(Integer adsId, Integer commentId);

    Optional<CommentModel> getByAdsIdAndId(Integer adsId, Integer commentId);
    Collection<CommentModel> getByAdsId(Integer adsId);

}
