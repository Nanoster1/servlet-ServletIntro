package letscode;

import letscode.Services.User;

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

public class MainServlet extends HttpServlet
{
    private static final String PathParameter = "path";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
    {
        String pathParam = req.getParameter(PathParameter);
        User user = UserRepository.instance.getUserFromCookie(req.getCookies());
        if (user == null)
        {
            resp.sendRedirect("/login");
            return;
        }

        System.out.println(pathParam);
        Path path = null;
        if (pathParam != null) path = Paths.get(pathParam).toAbsolutePath();

        if (path == null || pathParam.equals("null") || !path.toString().startsWith(System.getProperty("user.home")+"/"+user.getLogin()+"/"))
        {
            pathParam = System.getProperty("user.home")+"/"+user.getLogin();
        }

        path = Paths.get(pathParam).toAbsolutePath();

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
