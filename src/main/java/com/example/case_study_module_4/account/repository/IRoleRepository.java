package com.example.case_study_module_4.account.repository;

import com.example.case_study_module_4.account.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role,Integer> {
}
