package edu.bbte.idde.szim2182;

import edu.bbte.idde.szim2182.backend.dao.DaoFactory;
import edu.bbte.idde.szim2182.backend.dao.JdbcDaoFactory;
import edu.bbte.idde.szim2182.backend.dao.MemDaoFactory;
import edu.bbte.idde.szim2182.backend.datasource.ApplicationConfig;
import edu.bbte.idde.szim2182.backend.datasource.ConfigLoader;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebListener
public class AppConfigListener implements ServletContextListener {

    private static final Logger LOG = LoggerFactory.getLogger(AppConfigListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String profile = System.getProperty("app.profile", "dev");
        try {
            ApplicationConfig config = ConfigLoader.loadConfig(profile);
            sce.getServletContext().setAttribute("config", config);

            DaoFactory daoFactory;
            if ("prod".equals(profile)) {
                daoFactory = JdbcDaoFactory.getInstance();
            } else {
                daoFactory = MemDaoFactory.getInstance();
            }
            sce.getServletContext().setAttribute("daoFactory", daoFactory);

        } catch (IOException e) {
            LOG.error("Failed to load configuration", e);
            sce.getServletContext().setAttribute("configLoadError", e);
        }
    }

}
