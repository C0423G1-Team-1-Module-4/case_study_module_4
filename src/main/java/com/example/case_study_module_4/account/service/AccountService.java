package com.example.case_study_module_4.account.service;

import com.example.case_study_module_4.account.model.Account;
import com.example.case_study_module_4.account.repository.IAccountRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.UUID;

@Service
@Lazy
public class AccountService implements IAccountService {
    @Autowired
    private IAccountRepository iAccountRepository;
    @Autowired
    private JavaMailSender mailSender;

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
        accountUser.setVerificationCode(UUID.randomUUID().toString());
        System.out.println(accountUser.getVerificationCode());
        accountUser.setStatus(false);
        iAccountRepository.save(accountUser);
    }

    @Override
    public void sendVerificationEmail(Account accountUser, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = accountUser.getEmail();
        String fromAddress = "trung11041990a1@gmail.com";
        String senderName = "TheHome!";
        String subject = "Confirm your email address";
        String content = "<body style=\"margin: 0; padding: 0\">\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse\">\n" +
                "  <tr>\n" +
                "    <td  style=\" background: #5cb1e7; \">\n" +
                "    </td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td bgcolor=\"#eaeaea\" style=\"padding: 30px 20px 40px 30px;background: url('t') no-repeat center center;background-size: cover;\">\n" +
                "\n" +
                "      <p>Dear<span style=\"color: #0db9e0;font-size: 14px;font-weight: bold;\"> " + accountUser.getUsername() + "</span></p>\n" +
                "      <p >\n" +
                "        We have received a request to reset the password associated with your account. In order to proceed with\n" +
                "        the password reset process, we need to confirm that the email address associated with your account is valid.<br>\n" +
                "        Please click on the following link to confirm your email address:<br>\n" +
                "      </p>";
        String verifyURL = siteURL + "/verify?code=" + accountUser.getVerificationCode();
        content += "      <button style=\"background-color: #2093c7; \n" +
                "   border: none;\n" +
                "  color: #ffffff;\n" +
                "  padding: 16px 32px;\n" +
                "  text-align: center;\n" +
                "  text-decoration: none;\n" +
                "  display: inline-block;\n" +
                "  font-size: 16px;\n" +
                "  margin: 4px 2px;\n" +
                "  justify-content: center;\n" +
                "  transition-duration: 0.4s;\n" +
                "  cursor: pointer; border-radius: 20px\"><span style='color: #ffffff'><a href=\"" + verifyURL + "\">Confirm Your Email Address</a></span></button>";
        content += "  <tr>\n" +
                "    <td style=\"padding: 10px 20px; color: #FFFFFF;background: #5cb1e7\">\n" +
                "      <p>If you did not create an account with us, please ignore this email.\n" +
                "        <br>\n" +
                "        Thank you for choosing Cinema. We look forward to serving you!<br>\n" +
                "        Best regards!<br></div></p>\n" +
                "    </td>\n" +
                "  </tr>\n" +
                "</table>\n" +
                "</body>";


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

    @Override
    public Account findByCode(String code) {
        Account account = iAccountRepository.findByVerificationCode(code);
        return account;
    }

    @Override
    public Boolean verifyReset(String code) {
        Account accountUser = iAccountRepository.findByVerificationCode(code);
        Calendar cal = Calendar.getInstance();
        if (accountUser == null || (accountUser.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return false;
        } else {
            accountUser.setVerificationCode(null);
            iAccountRepository.save(accountUser);
            return true;
        }
    }

    @Override
    public void reset(Account accountUser) {
        String randomCode = RandomString.make(64);
        accountUser.setVerificationCode(randomCode);
        iAccountRepository.save(accountUser);
    }

    @Override
    public void sendVerificationReset(Account accountUser, String siteURL) throws UnsupportedEncodingException, MessagingException {
        String toAddress = accountUser.getEmail();
        String fromAddress = "phantaanhdao@gmail.com";
        String senderName = "Cinema";
        String subject = "Confirm your email address";
        String content = "<body  style=\"margin: 0; padding: 0\">\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse\">\n" +
                "  <tr>\n" +
                "    <td  style=\" background: #5cb1e7; \">\n" +
                "    </td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td bgcolor=\"#eaeaea\" style=\"padding: 30px 20px 40px 30px;background: url('') no-repeat center center;background-size: cover;\">\n" +
                "\n" +
                "      <p>Dear<span style=\"color: #0db9e0;font-size: 14px;font-weight: bold;\"> \"" + accountUser.getUsername() + "\" </span></p>\n" +
                "      <p >\n" +
                "        We have received a request to reset the password associated with your account. In order to proceed with\n" +
                "        the password reset process, we need to confirm that the email address associated with your account is valid.<br>\n" +
                "        Please click on the following link to confirm your email address:<br>\n" +
                "      </p>";
        String verifyURL = siteURL + "/verify_reset?code=" + accountUser.getVerificationCode();
        content += "      <button style=\"background-color: #2093c7; \n" +
                "   border: none;\n" +
                "  color: #ffffff;\n" +
                "  padding: 16px 32px;\n" +
                "  text-align: center;\n" +
                "  text-decoration: none;\n" +
                "  display: inline-block;\n" +
                "  font-size: 16px;\n" +
                "  margin: 4px 2px;\n" +
                "  justify-content: center;\n" +
                "  transition-duration: 0.4s;\n" +
                "  cursor: pointer; border-radius: 20px\"><span style='color: #ffffff'><a href=\"" + verifyURL + "\">Confirm Your Email Address</a><span></button>";
        content += "  <tr>\n" +
                "    <td style=\"padding: 10px 20px; color: #FFFFFF;background: #5cb1e7\">\n" +
                "      <p>If you did not request a password reset, please disregard this message.\n" +
                "        <br>\n" +
                "        If you believe that your account has been compromised, please contact our customer support team immediately.<br>\n" +
                "        Thank you,<br></div></p>\n" +
                "    </td>\n" +
                "  </tr>\n" +
                "</table>\n" +
                "</body>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

    @Override
    public boolean verify(String code) {
        Account accountUser = iAccountRepository.findByVerificationCode(code);
        Calendar cal = Calendar.getInstance();
        if (accountUser == null || accountUser.isStatus()) {
            return false;
        } else if ((accountUser.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            iAccountRepository.delete(accountUser);
            return false;
        } else {
            accountUser.setExpiryDate(null);
            accountUser.setVerificationCode(null);
            accountUser.setStatus(true);
            iAccountRepository.save(accountUser);
            return true;
        }
    }

    @Override
    public void reset_pw(Account accountUser, String newPw) {
        Account oldUser = iAccountRepository.findAccountUserByEmail(accountUser.getEmail());
        accountUser.setPassword(newPw);
        accountUser.setPassword(BCrypt.hashpw(accountUser.getPassword(), BCrypt.gensalt(12)));
        oldUser.setPassword(accountUser.getPassword());
        iAccountRepository.save(oldUser);
    }


}
