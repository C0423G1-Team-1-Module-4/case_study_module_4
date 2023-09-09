package com.example.case_study_module_4.account.repository;

import com.example.case_study_module_4.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAccountRepository extends JpaRepository<Account,Integer> {
    Account findAccountByUsername(String username);
    Account findAccountUserByEmail(String email);
    Account findByVerificationCode(String code);
}
