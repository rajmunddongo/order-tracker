package onlab.aut.bme.hu.java.controller;

import lombok.RequiredArgsConstructor;
import onlab.aut.bme.hu.java.entity.User;
import onlab.aut.bme.hu.java.service.MailService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class MailController {

    private final MailService mailService;

    @PostMapping("/mail/welcome")
    public ResponseEntity<String> sendWelcomeEmail(User user) {
        mailService.sendWelcomeMail(user.getEmail(), user.getFirstname());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth/mail/forgotpass")
    public ResponseEntity<String> forgotPassword(@RequestBody String email){
        return mailService.resetPassword(email);
    }
}
