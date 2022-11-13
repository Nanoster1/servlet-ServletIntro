package letscode;

import letscode.Services.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/register")
public class RegistrationServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
    {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/register.jsp");
        requestDispatcher.forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (login != null && password != null && email != null)
        {
            User user = new User(login,password,email);
            if(UserRepository.instance.addUser(user))
            {
                resp.sendRedirect("/login");
            }
            else
            {
                resp.sendRedirect("/register");
            }
        }
    }

}
