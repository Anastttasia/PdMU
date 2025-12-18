package com.finalproject.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.finalproject.finalproject.model.VisaApplicationModel;

import java.util.ArrayList;

public interface VisaApplicationRepository extends JpaRepository<VisaApplicationModel, String> {

    ArrayList<VisaApplicationModel> findByUserId(Integer user_id);
    VisaApplicationModel findById(Integer id);
}