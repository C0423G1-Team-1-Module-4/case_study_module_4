package com.example.case_study_module_4.account.dto;

import com.example.case_study_module_4.account.model.Role;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

public class AccountDto implements Validator {
    private int id;

    private String username;

    private String password;

    private String email;
    private Role role;

    private boolean status;
    private String verificationCode;
    private Date expiryDate;

    public AccountDto() {
    }


    public AccountDto(int id, String username, String password, String email, Role role, boolean status, String verificationCode, Date expiryDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.status = status;
        this.verificationCode = verificationCode;
        this.expiryDate = expiryDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }



    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountDto accountUserDto = (AccountDto) target;
        if (accountUserDto.getUsername().trim().isEmpty()) {
            errors.rejectValue("username", "", "User name cound not be void!");
        } else if (accountUserDto.getUsername().length() < 5) {
            errors.rejectValue("username", "", "Your UserName must be at least 6 characters or more!");
        }
        if (accountUserDto.getEmail().trim().equals("")) {
            errors.rejectValue("email", "", "Email cannot is empty!");
        } else if (!accountUserDto.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            errors.rejectValue("email", "", "Invalid email format!");
        }
        if (accountUserDto.getPassword().trim().equals("")) {
            errors.rejectValue("password", "", "Password cannot is empty!");
        } else if (!accountUserDto.getPassword().matches("^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$")) {
            errors.rejectValue("password", "", "Invalid password format!");
        }
    }
}
