package com.example.case_study_module_4.account.controller;

import com.example.case_study_module_4.account.dto.AccountDto;
import com.example.case_study_module_4.account.model.Account;
import com.example.case_study_module_4.account.model.Role;
import com.example.case_study_module_4.account.service.IAccountService;
import com.example.case_study_module_4.model.customer.Customer;
import com.example.case_study_module_4.service.customer.ICustomerService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Controller
public class AccountController {
    @Autowired
    IAccountService iAccountService;
    @Autowired
    ICustomerService iCustomerService;

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("accountDto", new AccountDto());
        return "account/login";
    }
    @GetMapping("/logoutSuccessful")
    public String logout(Model model, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.clearContext();
            redirectAttributes.addFlashAttribute("message", "Logout successfully!");
        }
        return "redirect:/";
    }
    @GetMapping("/email")
    public String showRemember(Model model){
        model.addAttribute("account" ,new AccountDto());
        return "account/rememberMe";
    }
    @GetMapping("/404")
    public String change404(Model model){
        return "shop/404";
    }

    @GetMapping("/userInfo")
    public String userInfo(Model model, Principal principal, RedirectAttributes redirectAttributes) {
        String userName = principal.getName();
        Account account = iAccountService.findByUserName(userName);
        if (account.getRole().getRoleName().equals("ROLE_USER")) {
            if (!account.isStatus()) {
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
    public String signUp(@Valid @ModelAttribute AccountDto accountDto, BindingResult bindingResult, HttpServletRequest request, Model model,RedirectAttributes redirectAttributes) throws MessagingException, UnsupportedEncodingException {
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
            accountUser.setExpiryDate(calculateExpiryDate());
//          System.out.println(accountUser.getExpiryDate());
            Role role = new Role();
            role.setId(1);
            accountUser.setRole(role);
            System.out.println(accountUser.getVerificationCode());
            iAccountService.createAccount(accountUser);
            Customer customer = new Customer(accountUser);
            iCustomerService.save(customer);
            String siteURL = getSiteURL(request);
            iAccountService.sendVerificationEmail(accountUser, siteURL);
            redirectAttributes.addFlashAttribute("success", "Please check your gmail to confirm your subscription");
        }
        return "redirect:/login";
    }
    @PostMapping("/confirm_email")
    public String confirm_email(@Valid @ModelAttribute AccountDto accountUserDto, @RequestParam("email") String email, HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) throws UnsupportedEncodingException, MessagingException {
        if (iAccountService.findByEmail(accountUserDto.getEmail()) == null) {
            model.addAttribute("fail", "This email don't exists or invalid email format!");
            System.out.println(accountUserDto.getEmail());
            return "account/email_reset_pw";
        }
        Account accountUser = iAccountService.findByEmail(email);
        accountUser.setExpiryDate(calculateExpiryDate());
        iAccountService.reset(accountUser);
        String siteURL = getSiteURL(request);
        iAccountService.sendVerificationReset(accountUser, siteURL);
        redirectAttributes.addFlashAttribute("success", "Please check your email to verify your account.");
        return "redirect:/login";
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestParam("code") String code, RedirectAttributes redirectAttributes) {
        if (iAccountService.verify(code)) {
            redirectAttributes.addFlashAttribute("success", "Congratulations, your account has been verified.");
        } else {
            redirectAttributes.addFlashAttribute("fail", "Sorry, we could not verify account. It maybe already verified, or verification code is incorrect.");
        }
        return "redirect:/login";
    }
    @GetMapping("/verify_reset")
    public String verify_reset(@RequestParam("code") String code, Model model,
                               RedirectAttributes redirectAttributes) {
        String email = null;
        if (iAccountService.findByCode(code) != null) {
            Account accountUser = iAccountService.findByCode(code);
            email = accountUser.getEmail();
        }
        if (iAccountService.verifyReset(code)) {
            Account accountUser = iAccountService.findByEmail(email);
            model.addAttribute("account", accountUser);
            return "account/reset_pw";
        } else {
            redirectAttributes.addFlashAttribute("fail", "Sorry, we could not verify account. It maybe already verified, or verification code is incorrect.");
            return "redirect:/login";
        }
    }
    @GetMapping("/reset_pw")
    public String reset_pw(@ModelAttribute AccountDto accountUserDto, Model model) {
        model.addAttribute("accountUserDto", new AccountDto());
        return "account/reset_pw";
    }
    @PostMapping("/new_pw")
    public String new_pw(@ModelAttribute Account accountUser, @RequestParam("new_pw") String new_pw,
                         RedirectAttributes redirectAttributes) {
        iAccountService.reset_pw(accountUser, new_pw);
        redirectAttributes.addFlashAttribute("success", "Password change successful.");
        return "redirect:/login";
    }
    private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, 1);
        return new Date(cal.getTime().getTime());
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
