package SpringApp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.regex.Pattern;

public class ValidationController {
    protected static final Pattern emailPat = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    protected static final Pattern namePat = Pattern.compile("^[A-Z](?=.{2,29}$)[A-Za-z]*(?:\\h+[A-Z][A-Za-z]*)*$");
    protected static final Pattern passwordPat = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$");

    public static void validateNewUser(String name, String password, String email) {

        if (!ValidationController.isValid(emailPat,email.trim())) {
            System.out.println("Invalid Email (example@ex.com)");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Invalid Email");
        }
        if (!ValidationController.isValid(namePat,name.trim())) {
            System.out.println("Invalid Name must contain only A-Z a-z(must begin with A-Z)");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Invalid Name");
        }
        if (!ValidationController.isValid(passwordPat,password)) {
            System.out.println("Invalid password : Password must be at least 8 characters and contains: A-Z,a-z,0-9");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Invalid password");
        }
    }

    public static boolean isValid(Pattern p, String input) {
        return p.matcher(input).matches();
    }
}
