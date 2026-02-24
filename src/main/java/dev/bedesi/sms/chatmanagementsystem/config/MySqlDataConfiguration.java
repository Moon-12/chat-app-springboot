package dev.bedesi.sms.chatmanagementsystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.orm.jpa.JpaVendorAdapter;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages ="dev.bedesi.sms.chatmanagementsystem.mysql")
public class MySqlDataConfiguration {

    @Value("${oracle2.driver}")
    private String oracleDriver;

    @Value("${oracle2.url}")
    private String oracleUrl;

    @Value("${oracle2.username}")
    private String oracleUsername;

    @Value("${oracle2.dialect}")
    private String hibernateDialect;

    @Bean
    public DataSource dataSource(){
        String envPassword = System.getenv("MYSQL_PASSWORD");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(oracleDriver);
        dataSource.setUrl(oracleUrl);
        dataSource.setUsername(oracleUsername);
        dataSource.setPassword(envPassword);
        return dataSource;
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("dev.bedesi.sms.chatmanagementsystem.mysql"); // Adjust if entities are in a different package

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.dialect", hibernateDialect);
        em.setJpaProperties(jpaProperties);

        return em;
    }


}
