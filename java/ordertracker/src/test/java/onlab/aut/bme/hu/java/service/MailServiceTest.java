package onlab.aut.bme.hu.java.service;

import jakarta.mail.internet.MimeMessage;
import onlab.aut.bme.hu.java.entity.User;
import onlab.aut.bme.hu.java.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestComponent
@ExtendWith(MockitoExtension.class)
public class MailServiceTest {

    @InjectMocks
    MailService service;
    @Mock
    JavaMailSender javaMailSender;
    @Mock
    UserRepository userRepository;
    @Captor
    private ArgumentCaptor<SimpleMailMessage> messageCaptor;

    @Test
    void sendWelcomeMailTest() {
        service.sendWelcomeMail("email","username");
        verify(javaMailSender).send(messageCaptor.capture());
        SimpleMailMessage mailMessage = messageCaptor.getValue();
        assertEquals("Welcome to Order tracker!",mailMessage.getSubject());
    }

    @Test
    void resetPasswordTest() {
        User user = new User();
        user.setEmail("eamil");
        when(userRepository.findByEmail("email")).thenReturn(Optional.of(user));
        service.resetPassword("email");
        verify(javaMailSender).send(messageCaptor.capture());
        SimpleMailMessage mailMessage = messageCaptor.getValue();
        assertEquals("Password reset for Order Tracker",mailMessage.getSubject());
    }
}
