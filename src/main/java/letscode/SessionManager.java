package letscode;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.nio.file.Files;

public class SessionManager {
    private static SessionFactory sessionFactory;

    static{
        try {
            Configuration config = new Configuration();
            File file = new File("src/main/webapp/hibernate.cfg.xml");
            System.out.println(file.getAbsolutePath());
            config.configure(file);
            sessionFactory = config.buildSessionFactory();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Session getSession(){
        return sessionFactory.openSession();
    }
}