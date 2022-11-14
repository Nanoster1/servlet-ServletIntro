package letscode;

import letscode.Services.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.Cookie;

public class UserRepositoryJdbc implements IUserRepository {
    private Connection connectionNow;

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connectionNow != null && !connectionNow.isClosed()) {
            return connectionNow;
        }
        Class.forName("org.postgresql.Driver");
        connectionNow = DriverManager.getConnection("jdbc:postgresql://192.168.0.129:5432/lab6", "postgres", "nanoster2");
        return connectionNow;
    }

    public boolean addUser(User user) {
        try {
            PreparedStatement st = getConnection().prepareStatement(
                    "INSERT INTO users (login, password, email) VALUES (?, ?, ?)");
            st.setString(1, user.getLogin());
            st.setString(2, user.getPassword());
            st.setString(3, user.getEmail());
            st.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public User getUserByLogin(String login) {
        try {
            PreparedStatement st = getConnection().prepareStatement("SELECT login, password, email FROM users WHERE login = ?");
            st.setString(1, login);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("email"));
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public User getUserFromCookie(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("login".equals(cookie.getName())) {
                    return this.getUserByLogin(cookie.getValue());
                }
            }
        }
        return null;
    }
}