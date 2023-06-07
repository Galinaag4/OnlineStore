package com.example.onlinestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.onlinestore.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository <UserModel, Long>{

}
