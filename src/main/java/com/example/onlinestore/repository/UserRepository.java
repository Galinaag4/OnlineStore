package com.example.onlinestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.onlinestore.model.UserModel;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <UserModel, Integer>{
    Optional<UserModel> findByUsername(String username);
    Integer getImageById(Integer userModelId);


}
