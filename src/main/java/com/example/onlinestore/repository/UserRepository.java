package com.example.onlinestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.onlinestore.model.UserModel;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <UserModel, Long>{
    Optional<UserModel> findByUsername(String username);
    Optional<UserModel> findUserByEmail(String email);
    Integer getUserModelId(String name);


}
