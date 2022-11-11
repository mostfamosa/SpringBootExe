package repository;

import entity.User;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class Repo {

    private final File[] files;
    private static Repo repo;
    private final Map<Integer, User> users;
   // private final Gson gson;
    private final String filepath;

    private Repo() {
        filepath = "src/main/resources/Users/";
       // gson = new Gson();
        files = new File("src/main/resources/Users/").listFiles();
        users = new HashMap<>();
        for (int i = 0; i < 30; i++) {
            users.put(User.newRandomUser().getId(), User.newRandomUser());
        }

    }

    public static Repo getInstance() {
        if (repo == null) {
            repo = new Repo();
        }
        return repo;
    }

//    protected Map<Integer, User> getUsers() {
//        return users;
//    }
//
//    protected User getUserById(Integer id) {
//        return users.get(id);
//    }

//    protected boolean deleteUser(Integer id) {
//        users.remove(id);
//        File file = new File(filepath + id + ".json");
//        return file.delete();
//    }

    protected void saveNewUser(User user) {
        writeToFile("" + user.getId() + ".json", user);
    }

    private void writeToFile(String filename, User content) {
        try (FileWriter writer = new FileWriter(filepath + filename)) {
          //  gson.toJson(content, writer);
            users.put(content.getId(), content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    Map<Integer, User> loadAllUsers() {
//        HashMap<Integer, User> users= new HashMap<>();
//        for (File file : files) {
//            User u = readFromFile(file.getName());
//            assert u != null;
//            users.put(u.getId(), u);
//        }
//        return users;
//    }

//    private User readFromFile(String fileName) {
//        try (FileReader reader = new FileReader(filepath + fileName)) {
//           // User u = gson.fromJson(reader, User.class);
//            reader.close();
//            return u;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    protected void updateUser(User user) {
//        try (FileWriter writer = new FileWriter(filepath + user.getId() + ".json")) {
//            gson.toJson(user, writer);
//            users.put(user.getId(), user);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    protected int maximumId(){
        return users.keySet().stream().max(Integer::compareTo).orElse(0);
    }

    public User createUser(User user) {
        users.put(user.getId(), user);
        return user;
    }
}
