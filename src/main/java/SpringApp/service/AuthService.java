package SpringApp.service;

import SpringApp.entity.User;
import SpringApp.repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AuthService {

    private static AuthService authService;
    @Autowired
    private Repo repo;
    private AtomicInteger idCounter;
    private Map<UUID, Integer> tokens;

    public AuthService(Repo repo, Map<UUID, Integer> tokens) {
        this.repo = repo;
        this.tokens = tokens;
        setIdCounter();
    }

    public static AuthService getInstance() {
        if (authService == null) {
            Map<UUID, Integer> tokens = new HashMap<>();
            authService = new AuthService(Repo.getInstance(),tokens);
        }
        return authService;
    }

    public User createUser(User user) {
        if (emailExists(user.getEmail())) {
            System.out.println("Error: This email already exists");
            throw new ResponseStatusException(HttpStatus.FOUND,"This email already exists");
        }
        user.setId(idCounter.getAndIncrement());
        return repo.createUser(user);
    }

    public Integer isLoggedIn(UUID token){
        if (tokens.get(token) == null){
            System.out.println("Error: Invalid token");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Invalid token");

        }
        return tokens.get(token);
    }

    public UUID login(User user) {
        Optional<User> myuser = validLoginCredentials(user.getEmail(), user.getPassword());
        UUID token;

        if(myuser.isPresent()){
            token = UUID.randomUUID();
            tokens.put(token , myuser.get().getId());
            System.out.println(myuser.get() + " Is logged in ");
        } else {
            token = new UUID(0L, 0L);
            System.out.println("Error: No such registered user with this credentials");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User Not Found");

        }
        return token;
    }

    private boolean emailExists(String email) {
        return repo.getUsers().values().stream().anyMatch(user -> user.getEmail().equals(email));
    }

    private Optional<User> validLoginCredentials(String email, String password){
        return repo.getUsers().values().stream().filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password)).findFirst();
    }

    public void removeToken(UUID token){
        tokens.remove(token);
    }

    private void setIdCounter(){idCounter = new AtomicInteger(repo.maximumId()+1);}
}

