
package com.finalproject.finalproject.repository;

import com.finalproject.finalproject.model.MigrantModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MigrantsRepository extends JpaRepository<MigrantModel, String> {
    MigrantModel findByPassport(String passport);
}