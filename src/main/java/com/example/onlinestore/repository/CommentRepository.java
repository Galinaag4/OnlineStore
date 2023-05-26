package com.example.onlinestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.onlinestore.model.CommentModel;
@Repository
public interface CommentRepository extends JpaRepository <CommentModel, Long> {
}
