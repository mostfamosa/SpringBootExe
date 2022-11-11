package SpringApp.service;

import SpringApp.InputsTypes;
import SpringApp.entity.User;
import SpringApp.repository.Repo;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static UserService userService;
    private final Repo repo;

    private UserService() {
        repo = Repo.getInstance();
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public String update(int userId, String data, InputsTypes type) {
        User user = repo.getUserById(userId);
        if (user != null) {
            switch (type) {
                case EMAIL:
                    user.setEmail(data);
                    repo.updateUser(user);
                    System.out.println("User "+userId+"Email updated to : "+data);
                    return ("User "+userId+" Email has been updated to:"+data);
                case PASSWORD:
                    user.setPassword(data);
                    repo.updateUser(user);
                    System.out.println("User "+userId+"Password updated to : "+data);
                    return ("User "+userId+" Password has been updated to:"+data);
                case NAME:
                    user.setName(data);
                    repo.updateUser(user);
                    System.out.println("User "+userId+"Name updated to : "+data);
                    return ("User "+userId+" Name has been updated to:"+data);
                default:
                    System.out.println("type not good");
                    return("type not good");
            }
        }
        return("user invalid");

    }


    public String deleteUser(Integer id) {
        if (repo.deleteUser(id)) {
            System.out.println("User"+id+": deleted successfully");
            return "User"+id+" :deleted successfully";
        } else {
            System.out.println("Error: user deletion failed");
            return "Error: user"+id+": deletion failed";
        }
    }
}