package com.koreanunited.webflix;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

import oracle.jdbc.pool.OracleDataSource;

@EnableJpaRepositories(basePackages = "com.koreanunited.webflix.repository")
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
    
    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }
    
    @Bean
    DataSource dataSource() throws SQLException {
    	 
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser("equipe7");
        dataSource.setPassword("r74w84K3");
        dataSource.setURL("jdbc:oracle:thin:@log660ora12c.logti.etsmtl.ca:1521:LOG660");
        dataSource.setImplicitCachingEnabled(true);
        dataSource.setFastConnectionFailoverEnabled(true);
        return dataSource;
    }

    @Bean
    public FilterRegistrationBean registerOpenSessionInViewFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        OpenEntityManagerInViewFilter filter = new OpenEntityManagerInViewFilter();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
