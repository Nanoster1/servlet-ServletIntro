package letscode;

import letscode.Services.User;
import javax.servlet.http.Cookie;
import org.hibernate.Session;

public class UserRepository {
    public static UserRepository instance = new UserRepository();

    public boolean addUser(User user){
        try(Session session = SessionManager.getSession()){
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }
    public User getUserByLogin(String login){
        User user = null;
        try(Session session = SessionManager.getSession()){
            user = session.byNaturalId(User.class).using("login",login).load();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return user;
        }
        return user;
    }
    public User getUserFromCookie(Cookie[] cookies){
        if(cookies !=null) {
            for(Cookie cookie: cookies) {
                if("login".equals(cookie.getName())) {
                    return this.getUserByLogin(cookie.getValue());
                }
            }
        }
        return null;
    }
}