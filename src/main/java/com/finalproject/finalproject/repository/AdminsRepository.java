
package com.finalproject.finalproject.repository;

import com.finalproject.finalproject.model.AdminModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminsRepository extends JpaRepository<AdminModel, String> {
    AdminModel findByEmail(String email);
    AdminModel findByAdminId(Integer adminId);
}