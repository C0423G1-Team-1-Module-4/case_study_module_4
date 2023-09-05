package com.example.case_study_module_4.account.service;

import com.example.case_study_module_4.account.model.Account;

public interface IAccountService {
    Account findByUserName(String userName);

    Account findByEmail(String name);

    void createAccount(Account accountUser);
}
