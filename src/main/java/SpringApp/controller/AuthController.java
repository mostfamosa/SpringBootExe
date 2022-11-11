package SpringApp.controller;

import SpringApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import SpringApp.service.AuthService;
import org.springframework.web.server.ResponseStatusException;
import java.util.UUID;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    private static final Pattern PasswordPattern =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$");
    private static final Pattern emailPattern =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);



    /*hi method*/
    @RequestMapping(value = "hi",method = RequestMethod.POST)
    public  ResponseEntity<User> hi(@RequestBody User u){
        return ResponseEntity.ok(u);
    }

    /*Login method*/
    @RequestMapping(value = "login" , method = RequestMethod.POST)
    public  ResponseEntity<UUID> logIn(@RequestBody User user){
        if (!ValidationController.isValid(emailPattern,user.getEmail().trim()))
        {
            System.out.println(this.getClass().getName()+": Invalid Email (example@ex.com)");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Invalid Email");
        }
        if (!ValidationController.isValid(PasswordPattern,user.getPassword()))
        {
            System.out.println(this.getClass().getName()+": Invalid password : Password must be at least 8 characters and contains: A-Z,a-z,0-9");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Invalid password");
        }
        return ResponseEntity.ok(authService.login(user));
    }

    /*Create New User method*/
    @RequestMapping(value = "create",method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody User user) {
        ValidationController.validateNewUser(user.getName(), user.getPassword(), user.getEmail());
        return ResponseEntity.ok(authService.createUser(user));
    }


}
