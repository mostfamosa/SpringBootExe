package service;

import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.Repo;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AuthService {

    private static AuthService authService;
    @Autowired
    private Repo repo;
    private static AtomicInteger idCounter;





    public User createUser(User user) {
        return repo.createUser(user);
    }

//    protected Integer isLoggedIn(UUID token){
//        if (tokens.get(token) == null){
//            System.out.println("Error: Invalid token");
//        }
//
//        return tokens.get(token);
//    }

//    protected UUID login(String email, String password) {
//        Optional<User> user = validLoginCredentials(email, password);
//        UUID token;
//
//        if(user.isPresent()){
//            token = UUID.randomUUID();
//            tokens.put(token , user.get().getId());
//            System.out.println(user.get() + " Is logged in ");
//        } else {
//            token = new UUID(0L, 0L);
//            System.out.println("Error: No such registered user with this credentials");
//        }
//
//        return token;
//    }

//    private boolean emailExists(String email) {
//        return repo.getUsers().values().stream().anyMatch(user -> user.getEmail().equals(email));
//    }
//
//    private Optional<User> validLoginCredentials(String email, String password){
//        return repo.getUsers().values().stream().filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password)).findFirst();
//    }
//
//    protected void removeToken(UUID token){
//        tokens.remove(token);
//    }

//    private void setIdCounter(){idCounter = new AtomicInteger(repo.maximumId()+1);}
}

