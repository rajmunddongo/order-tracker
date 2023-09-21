package onlab.aut.bme.hu.java.service;

import lombok.RequiredArgsConstructor;
import onlab.aut.bme.hu.java.domain.GetProfileInfoResponse;
import onlab.aut.bme.hu.java.entity.Address;
import onlab.aut.bme.hu.java.entity.Merchant;
import onlab.aut.bme.hu.java.entity.User;
import onlab.aut.bme.hu.java.repository.MerchantRepository;
import onlab.aut.bme.hu.java.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final MerchantRepository merchantRepository;
    private final FileManagerService fileManagerService;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<User> getUserFromJWT(String token) {
        String email = jwtService.extractUsername(token.substring(7));
        Optional<User> user = repository.findByEmail(email);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public GetProfileInfoResponse getUserInfo(String header, String url) {
        User user = this.getUserFromJWT(header).getBody();
        if(user == null) throw new IllegalStateException("Cannot add");
        Address address = user.getMerchant() == null ? user.getCustomer().getAddress() : user.getMerchant().getAddress();
        return GetProfileInfoResponse.builder()
                .name(user.getUsername())
                .address(address.toString())
                .profilePicture(fileManagerService.downloadFile(url))
                .build();
    }

    public String setProfilePicture(Integer id, String picture) {
        User user = repository.findById(id).orElseThrow();
        if(user.getMerchant() != null) {
            Merchant merchant = user.getMerchant();
            merchant.setPicture(picture);
            merchantRepository.save(merchant);
        }
        user.setProfilePicture(picture);
        return repository.save(user).getUsername();
    }

    public void resetPassword(String password,String link) throws IllegalAccessException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS");
        if(LocalDateTime.parse(link.substring(36).substring(0,22),formatter).isBefore(LocalDateTime.now())) {
            throw new IllegalAccessException("Link expired");
        }
        User user = repository.findByPassResetLink(link).orElseThrow();
        user.setPassword(passwordEncoder.encode(password));
        repository.save(user);
    }
}
