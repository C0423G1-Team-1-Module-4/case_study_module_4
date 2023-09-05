package com.example.case_study_module_4.account.service;

import com.example.case_study_module_4.account.model.Account;
import com.example.case_study_module_4.account.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService{
    @Autowired
    IAccountRepository iAccountRepository;
    @Override
    public Account findByUserName(String userName) {
        return iAccountRepository.findAccountByUsername(userName);
    }

    @Override
    public Account findByEmail(String name) {

        return iAccountRepository.findAccountUserByEmail(name);
    }

    @Override
    public void createAccount(Account accountUser) {
        iAccountRepository.save(accountUser);
    }
}
