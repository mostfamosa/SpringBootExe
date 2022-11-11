package controller;

import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.AuthService;

@RestController
@RequestMapping("/user")
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(authService.createUser(user));
    }

}
