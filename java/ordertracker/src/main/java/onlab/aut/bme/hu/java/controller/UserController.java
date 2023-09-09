package onlab.aut.bme.hu.java.controller;

import lombok.RequiredArgsConstructor;
import onlab.aut.bme.hu.java.domain.GetProfileInfoResponse;
import onlab.aut.bme.hu.java.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile/info")
    public ResponseEntity<GetProfileInfoResponse> getProfileInfo(@RequestHeader("Authorization") String authorizationHeader, @RequestParam("name") String name) {
        return new ResponseEntity<>(userService.getUserInfo(authorizationHeader, name),HttpStatus.OK);
    }

    @PatchMapping("/profile/picture")
    public ResponseEntity<String> changeProfilePicture(@RequestParam("id") Integer id, @RequestParam("picture") String picture) {
        return new ResponseEntity<>(userService.setProfilePicture(id, picture),HttpStatus.OK);
    }
}
