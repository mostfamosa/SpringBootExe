package SpringApp.controller;

import SpringApp.InputsTypes;
import SpringApp.service.AuthService;
import SpringApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
@RestController
@RequestMapping("/user")
public class UserController {

    private static UserController userController;
    @Autowired
    private final AuthService authService;
    @Autowired
    private final UserService userService;

    private UserController() {
        authService = AuthService.getInstance();
        userService = UserService.getInstance();

    }

    public static UserController getInstance() {
        if (userController == null) {
            userController = new UserController();
        }
        return userController;
    }
    @RequestMapping(value = "update/name" ,method = RequestMethod.PATCH)
    public ResponseEntity<String> updateName(@RequestHeader UUID token, @RequestParam String newName) {
        if (!ValidationController.isValid(ValidationController.namePat,newName.trim())) {
            System.out.println(this.getClass().getName()+": Invalid Name must contain only A-Z a-z (must begin in A-Z)");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Invalid Name");
        }
        return ResponseEntity.ok(auth(token,newName,InputsTypes.NAME));
    }

    @RequestMapping(value = "update/email" ,method = RequestMethod.PATCH)
    public ResponseEntity<String> updateEmail(@RequestHeader UUID token, @RequestParam String newEmail) {
        if (!ValidationController.isValid(ValidationController.emailPat,newEmail.trim())) {
            System.out.println(this.getClass().getName()+": Invalid Email (example@ex.com)");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Invalid Email");
        }
        return ResponseEntity.ok(auth(token,newEmail,InputsTypes.EMAIL));

    }

    @RequestMapping(value = "update/password" ,method = RequestMethod.PATCH)
    public ResponseEntity<String> updatePassword(@RequestHeader UUID token, @RequestParam String newPassword) {
        if (!ValidationController.isValid(ValidationController.passwordPat,newPassword)) {
            System.out.println(this.getClass().getName()+": Invalid password - Password must be at least 8 characters and contains: A-Z,a-z,0-9");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Invalid Password");

        }
        return ResponseEntity.ok(auth(token,newPassword,InputsTypes.PASSWORD));
    }

    public String auth(UUID token,String data,InputsTypes type){
        Integer userId = authService.isLoggedIn(token);
        if (userId != null) {
            return userService.update(userId,data,type);
        }
        System.out.println("User must login first");
        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"User must login first");

    }

    @RequestMapping(value = "delete" ,method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@RequestHeader UUID token){
        Integer myId = authService.isLoggedIn(token);
        if (myId != null){
            authService.removeToken(token);
            return ResponseEntity.ok(userService.deleteUser(Integer.valueOf(myId)));
        }
        System.out.println("In order to DELETE, User must login first");
        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"In order to DELETE, User must login first");

    }
}