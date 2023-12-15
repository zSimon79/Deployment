package edu.bbte.idde.szim2182.spring.dao.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("jdbc")
public class DataSourceFactory {
    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.username}")
    private String jdbcUsername;
    @Value("${spring.datasource.password}")
    private String jdbcPassword;
    @Value("${spring.datasource.connectionNumber}")
    private Integer connectionNumber;


    @Bean
    public DataSource getDataSource() {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(jdbcUsername);
        hikariConfig.setPassword(jdbcPassword);

        hikariConfig.setMaximumPoolSize(connectionNumber);

        return new HikariDataSource(hikariConfig);
    }
}
