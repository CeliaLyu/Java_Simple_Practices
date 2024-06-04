import java.util.List;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public void registerUser(String firstName, String lastName, int role, String password, String userName) {
        User user = new User(firstName, lastName, role, password, userName);
        userDAO.addUser(user);
    }

    public User loginUser(String username, String password) {
        User user = userDAO.findUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}