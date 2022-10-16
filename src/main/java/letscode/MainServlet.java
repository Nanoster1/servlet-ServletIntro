package letscode;

import letscode.Services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainServlet extends HttpServlet
{
    private static final String PathParameter = "path";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
    {
        String pathParam = req.getParameter(PathParameter);
        UserService user = UserRepository.userRepository.getUserFromCookie(req.getCookies());
        if (user == null)
        {
            resp.sendRedirect("/WEB-INF/login");
        }
        if (pathParam == null || pathParam.equals("null") || !pathParam.startsWith("C:\\"+user.getLogin()+"\\"))
        {
            pathParam = "C:\\"+user.getLogin();
        }

        Path path = Paths.get(pathParam);

        if (!Files.exists(path))
        {
            resp.setStatus(404);
            resp.getWriter().write("Incorrect path");
            return;
        }

        File rootFile = path.toFile();
        if(!rootFile.isDirectory())
        {
            loadFile(resp,rootFile);
            return;
        }
        File[] subFiles = rootFile.listFiles();

        req.setAttribute("files", subFiles);
        req.setAttribute("rootFile", rootFile);
        req.setAttribute("attrs", Files.readAttributes(path, BasicFileAttributes.class));
        req.setAttribute("parentPath", req.getServletPath() + "?path=" + rootFile.getParent());

        getServletContext().getRequestDispatcher("/WEB-INF/main.jsp").forward(req, resp);
    }

    private void loadFile(HttpServletResponse resp, File file) throws IOException {
        resp.setContentType("text/html");
        resp.setHeader("Content-disposition", "attachment; filename=" + file.getName());

        OutputStream out = resp.getOutputStream();
        FileInputStream in = new FileInputStream(file);
        byte[] buffer = new byte[2048];
        int length;
        while ((length = in.read(buffer)) > 0){
            out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
    }
}
