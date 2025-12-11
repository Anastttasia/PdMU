
package com.finalproject.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.finalproject.finalproject.model.UserModel;

public interface UsersRepository extends JpaRepository<UserModel, String> {
    UserModel findByEmail(String email);
}