package onlab.aut.bme.hu.java.controller;

import lombok.RequiredArgsConstructor;
import onlab.aut.bme.hu.java.domain.GetProfileInfoResponse;
import onlab.aut.bme.hu.java.entity.User;
import onlab.aut.bme.hu.java.repository.UserRepository;
import onlab.aut.bme.hu.java.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/profile/info")
    public ResponseEntity<GetProfileInfoResponse> getProfileInfo(@RequestHeader("Authorization") String authorizationHeader, @RequestParam("name") String name) {
        return new ResponseEntity<>(userService.getUserInfo(authorizationHeader, name), HttpStatus.OK);
    }

    @PatchMapping("/profile/picture")
    public ResponseEntity<String> changeProfilePicture(@RequestParam("id") Integer id, @RequestParam("picture") String picture) {
        return new ResponseEntity<>(userService.setProfilePicture(id, picture), HttpStatus.OK);
    }

    @PostMapping("/auth/profile/resetpass")
    public ResponseEntity<String> resetPassword(@RequestBody String password, @RequestParam("pass") String link) throws IllegalAccessException {
        userService.resetPassword(password, link);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/auth/users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userRepository.findAll().stream()
                .filter(user -> !user.getEmail().equals("deleted")).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping("/auth/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
