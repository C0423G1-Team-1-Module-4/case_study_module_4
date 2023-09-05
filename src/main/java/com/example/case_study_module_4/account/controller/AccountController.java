package com.example.case_study_module_4.account.controller;

import com.example.case_study_module_4.account.dto.AccountDto;
import com.example.case_study_module_4.account.model.Account;
import com.example.case_study_module_4.account.model.Role;
import com.example.case_study_module_4.account.service.IAccountService;
import com.example.case_study_module_4.model.customer.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AccountController {
    @Autowired
    IAccountService iAccountService;

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("accountDto", new AccountDto());
        return "account/login";
    }
//    @GetMapping("/")
//    public String home(Model model){
//        model.addAttribute("account", new AccountDto());
//        return "product/index";
//    }

    @GetMapping("/userInfo")
    public String userInfo(Model model, Principal principal, RedirectAttributes redirectAttributes) {
        String userName = principal.getName();
        Account account = iAccountService.findByUserName(userName);
        if (account.getRole().getRoleName().equals("ROLE_USER")) {
            if (account.getStatus() == 1) {
                return "account/login";
            } else {
                model.addAttribute("info", account);
                model.addAttribute("accountName", userName);
                return "/";
            }
        } else if (account.getRole().getRoleName().equals("ROLE_ADMIN")) {
            model.addAttribute("info", account);
            model.addAttribute("accountName", userName);
            return "/";
        }
        return "/";
    }

    @PostMapping("/signUp")
    public String signUp(@Valid @ModelAttribute AccountDto accountDto, BindingResult bindingResult, HttpServletRequest httpServletRequest, Model model,RedirectAttributes redirectAttributes) {
        accountDto.validate(accountDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("fail", "Wrong input, please check");
            model.addAttribute("accountDto", accountDto);
            return "account/login";
        }
        if (iAccountService.findByEmail(accountDto.getEmail()) != null) {
            model.addAttribute("fail", "This email already exists!");
            return "account/login";
        } else if (iAccountService.findByUserName(accountDto.getUsername()) != null) {
            model.addAttribute("fail", "This user name already exists!");
            return "account/login";
        } else {
            Account accountUser = new Account();
            BeanUtils.copyProperties(accountDto, accountUser);
            accountUser.setPassword(BCrypt.hashpw(accountUser.getPassword(), BCrypt.gensalt(12)));
//            accountUser.setExpiryDate(calculateExpiryDate());
//            System.out.println(accountUser.getExpiryDate());
            Role role = new Role();
            role.setId(1);
            accountUser.setRole(role);
            iAccountService.createAccount(accountUser);
//            iCustomerService.cr(customer);
//            String siteURL = getSiteURL(request);
//            iAccountService.sendVerificationEmail(accountUser, siteURL);
            redirectAttributes.addFlashAttribute("success", "Sign Up Success");
        }
        return "redirect:/login";
    }
}
