package com.example.case_study_module_4.repository.emloyee;


import com.example.case_study_module_4.dto.emloyee.EmployeeDto;
import com.example.case_study_module_4.model.emloyee.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query(value = "select * from employee where employee_name like :name", nativeQuery = true)
    Page<Employee> findAll(Pageable pageable, @Param("name") String s);
    //tên,id_card,địa chỉ,giới tính,ngày sinh,lương,username,password
}
