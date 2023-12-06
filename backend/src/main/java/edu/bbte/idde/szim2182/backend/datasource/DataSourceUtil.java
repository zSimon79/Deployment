package edu.bbte.idde.szim2182.backend.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class DataSourceUtil {

    private static final HikariDataSource dataSource;
    private static final Logger LOG = LoggerFactory.getLogger(DataSourceUtil.class);

    static {
        HikariDataSource tempDataSource;
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://localhost:3306/HikeServlet");
            config.setUsername("Servlet");
            config.setPassword("1234");
            config.setDriverClassName("com.mysql.cj.jdbc.Driver"); // Explicitly set the driver class name
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            tempDataSource = new HikariDataSource(config);
        } catch (IllegalArgumentException | IllegalStateException e) {
            LOG.error("Configuration exception during HikariCP DataSource initialization", e);
            throw new ExceptionInInitializerError(e);
        }
        dataSource = tempDataSource;
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
