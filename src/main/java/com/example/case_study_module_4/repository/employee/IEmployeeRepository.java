package com.example.case_study_module_4.repository.employee;


import com.example.case_study_module_4.dto.employee.IEmployeeDto;
import com.example.case_study_module_4.model.employee.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query(value = "select ee.id as id, ee.employee_name as employeeName, ee.id_card as idCard, ee.address , " +
            "ee.gender, ee.birthdate, ee.salary, " +
            "acc.email, acc.status " +
            "from employee as ee " +
            "join account as acc " +
            "on ee.account_id = acc.id " +
            "where employee_name like :name or salary like :name or address like :name", nativeQuery = true)
    Page<IEmployeeDto> findAll(Pageable pageable, @Param("name") String s);

    @Transactional
    @Modifying
    @Query(value = "update account as acc " +
            "join employee as ee " +
            "on ee.account_id = acc.id " +
            "set acc.status = b'0' " +
            "where ee.id = :id",nativeQuery = true)
    void deleteById(@Param("id") int id);
    //tên,id_card,địa chỉ,giới tính,ngày sinh,lương,username,password
    //    private int id;
    //    private String employeeName;
    //    private String idCard;
    //    private String address;
    //    private int gender;
    //    private String birthdate;
    //    private int salary;
    //    private String email;
    //    private String imagePath;
    //    private String status;
}
