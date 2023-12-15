package edu.bbte.idde.szim2182.backend.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.szim2182.backend.dao.DaoException;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.IOException;

@Slf4j
public class DataSourceUtil {

    private static final HikariDataSource dataSource;

    static {
        ApplicationConfig appConfig;
        try {
            appConfig = ConfigLoader.loadConfig(System.getenv("iddeVariable")); //configfactory, egyszer
        } catch (IOException e) {
            log.error("Error loading configuration: {}", e.getMessage(), e);
            throw new DaoException("Error loading configuration: {}", e);
        }
        DatabaseConfig dbConfig = appConfig.getDatabaseConfig();
        HikariDataSource tempDataSource;
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbConfig.getUrl());
            config.setUsername(dbConfig.getUsername());
            config.setPassword(dbConfig.getPassword());
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            tempDataSource = new HikariDataSource(config);
        } catch (IllegalArgumentException | IllegalStateException e) {
            log.error("Configuration exception during HikariCP DataSource initialization", e);
            throw new ExceptionInInitializerError(e);
        }
        dataSource = tempDataSource;
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
