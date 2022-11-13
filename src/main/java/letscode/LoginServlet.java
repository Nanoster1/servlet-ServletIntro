package letscode;

import letscode.Services.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
    {
        String isLogout = req.getParameter("logout");
        if (isLogout != null && Boolean.valueOf(isLogout))
        {
            resp.addCookie(new Cookie("login", null));
            resp.addCookie(new Cookie("email", null));
            resp.addCookie(new Cookie("password", null));
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/login.jsp");
        requestDispatcher.forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login != null && password != null)
        {
            User user = UserRepository.instance.getUserByLogin(login);
            if(user != null && user.getPassword().equals(password))
            {
                resp.addCookie(new Cookie("login", user.getLogin()));
                resp.addCookie(new Cookie("email", user.getEmail()));
                resp.addCookie(new Cookie("password", user.getPassword()));
                resp.sendRedirect("/");
            }
            else
            {
                resp.sendRedirect("/login");
            }
        }
    }
}