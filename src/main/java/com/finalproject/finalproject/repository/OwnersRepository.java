
package com.finalproject.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalproject.finalproject.model.OwnerModel;

public interface OwnersRepository extends JpaRepository<OwnerModel, String> {
    OwnerModel findByUserId(Integer user_id);
}