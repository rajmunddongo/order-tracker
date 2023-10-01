package onlab.aut.bme.hu.java.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import onlab.aut.bme.hu.java.entity.Address;
import onlab.aut.bme.hu.java.entity.Merchant;
import onlab.aut.bme.hu.java.entity.User;
import onlab.aut.bme.hu.java.repository.MerchantRepository;
import onlab.aut.bme.hu.java.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestComponent
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService service;
    @Mock
    UserRepository repository;
    @Mock
    JwtService jwtService;
    @Mock
    MerchantRepository merchantRepository;
    @Mock
    FileManagerService fileManagerService;
    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void getUserFromJWTTest() {
        String jwt = Jwts.builder()
                .setSubject("test")
                .setExpiration(Date.valueOf(LocalDate.MAX))
                .signWith(SignatureAlgorithm.HS256, "404E635266556A586E3272343238782F413F4428472B4B6250645367566B5970")
                .compact();
        when(repository.findByEmail(any())).thenReturn(Optional.of(new User()));
        assertEquals(HttpStatus.OK, service.getUserFromJWT(jwt).getStatusCode());
        when(repository.findByEmail(any())).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, service.getUserFromJWT(jwt).getStatusCode());
    }

    @Test
        void getUserInfoTest() {
        String jwt = Jwts.builder()
                .setSubject("test")
                .setExpiration(Date.valueOf(LocalDate.MAX))
                .signWith(SignatureAlgorithm.HS256, "404E635266556A586E3272343238782F413F4428472B4B6250645367566B5970")
                .compact();
        when(fileManagerService.downloadFile(any())).thenReturn("mystring".getBytes());
        when(repository.findByEmail(any())).thenReturn(Optional.of(User.builder().merchant(Merchant.builder().address(new Address()).build()).build()));
        service.getUserInfo(jwt,jwt);
        verify(fileManagerService).downloadFile(any());
    }

    @Test
    void setProfilePictureTest() {
        User user = User.builder().merchant(new Merchant()).build();
        when(repository.findById(any())).thenReturn(Optional.ofNullable(user));
        when(repository.save(any())).thenReturn(user);
        service.setProfilePicture(1, "adwaw");
        verify(repository).save(any());
    }

    @Test
    void resetPasswordTest() throws IllegalAccessException {
        String link  = UUID.randomUUID()+ LocalDateTime.now().plusMinutes(15).toString();
        when(repository.findByPassResetLink(any())).thenReturn(Optional.of(new User()));
        service.resetPassword("pasd", link);
        verify(repository).save(any());
        link  = UUID.randomUUID()+ LocalDateTime.now().minusHours(15).toString();
        String finalLink = link;
        assertThrows(IllegalAccessException.class, () -> service.resetPassword("pasd", finalLink));

    }
}
