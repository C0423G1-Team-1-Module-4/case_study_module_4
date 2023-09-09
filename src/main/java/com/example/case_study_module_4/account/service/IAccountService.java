package com.example.case_study_module_4.account.service;

import com.example.case_study_module_4.account.model.Account;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface IAccountService {
    Account findByUserName(String userName);

    Account findByEmail(String name);

    void createAccount(Account accountUser);

    void sendVerificationEmail(Account accountUser, String siteURL) throws MessagingException, UnsupportedEncodingException;

    Account findByCode(String code);

    Boolean verifyReset(String code);

    void reset(Account accountUser);

    void sendVerificationReset(Account accountUser, String siteURL) throws UnsupportedEncodingException, MessagingException;

    boolean verify(String code);

    void reset_pw(Account accountUser, String newPw);
}
