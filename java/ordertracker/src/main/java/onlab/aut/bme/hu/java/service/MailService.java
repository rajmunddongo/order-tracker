package onlab.aut.bme.hu.java.service;

import lombok.RequiredArgsConstructor;
import onlab.aut.bme.hu.java.entity.User;
import onlab.aut.bme.hu.java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailService {

    @Autowired
    private final JavaMailSender javaMailSender;

    private final UserRepository userRepository;

    @Async
    public void sendWelcomeMail(String toEmail, String username) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setSubject("Welcome to Order tracker!");
        String text = "Dear "+username+",\n" +
                "\n" +
                "Welcome to OrderTracker – your ultimate order management solution! We're excited to have you on board.\n" +
                "\n" +
                "With OrderTracker, easily track and manage your orders in one place, receive real-time updates, and enjoy a personalized dashboard for seamless navigation.\n" +
                "\n" +
                "Get started now and simplify your order management. If you have any questions or need assistance, we're here to help!\n" +
                "\n" +
                "Best,\n" +
                "Rajmund Dongo\n" +
                "OrderTracker Team";
        simpleMailMessage.setText(text);
        simpleMailMessage.setFrom("ordertracker@no-reply.com");
        javaMailSender.send(simpleMailMessage);
    }

    @Async
    public ResponseEntity<String> resetPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());
        user.setPassResetLink(UUID.randomUUID()+ LocalDateTime.now().plusMinutes(15).toString());//36char UUID + Exp date as toString.
        userRepository.save(user);
        simpleMailMessage.setSubject("Password reset for Order Tracker");
        String text = "Dear "+user.getFirstname()+",\n" +
                "\n" +
                "We received a request to reset your password for your account. To reset your password, please follow the link below:\n" +
                "\n" +
                "http://localhost:4200/profile/resetpassword?pass="+user.getPassResetLink()+"\n" +
                "\n" +
                "If you did not request a password reset, please ignore this email. The link above will expire in 24 hours.\n" +
                "\n" +
                "Best regards,\n" +
                "Your Company Team";
        simpleMailMessage.setText(text);
        simpleMailMessage.setFrom("ordertracker@no-reply.com");
        javaMailSender.send(simpleMailMessage);
        return null;
    }
}
