package letscode;

import letscode.Services.User;
import org.hibernate.Session;

import javax.servlet.http.Cookie;

public interface IUserRepository {
    boolean addUser(User user);
    User getUserByLogin(String login);
    User getUserFromCookie(Cookie[] cookies);
}
