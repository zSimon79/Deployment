package edu.bbte.idde.szim2182;

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
        String profile = System.getProperty("app.profile", "prod");
        try {
            ApplicationConfig config = ConfigLoader.loadConfig(profile);
            sce.getServletContext().setAttribute("config", config);
        } catch (IOException e) {
            LOG.error("Failed to load configuration", e);
            sce.getServletContext().setAttribute("configLoadError", e);
        }
    }

}
