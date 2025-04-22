package dev.bedesi.sms.chatmanagementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages ="dev.bedesi.sms.samplemanagementsystem.mysql")
public class MySqlDataConfiguration {

    @Bean
    public DataSource dataSource(){
        String envDriver = System.getenv("MYSQL_DRIVER");
        String envUrl = System.getenv("MYSQL_URL");
        String envUsername = System.getenv("MYSQL_USERNAME");
        String envPassword = System.getenv("MYSQL_PASSWORD");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(envDriver);
        dataSource.setUrl(envUrl);
        dataSource.setUsername(envUsername);
        dataSource.setPassword(envPassword);
        return dataSource;
    }
}
