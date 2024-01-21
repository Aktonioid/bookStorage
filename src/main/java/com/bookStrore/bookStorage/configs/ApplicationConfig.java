package com.bookStrore.bookStorage.configs;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = 
{
    "com.bookStrore.bookStorage"
})
@EnableTransactionManagement
@PropertySource(value = "classpath:application.properties")
public class ApplicationConfig  
{
    @Autowired
    private Environment environment;


    @Bean 
    public DataSource dataSource()
    {
        
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));

        return dataSource;
    }
    
    private Properties hibernateProperties()
    {
        Properties properties = new Properties();

        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        properties.put("hibernate.highlight_sql", environment.getRequiredProperty("hibernate.highlight_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put("logging.level.org.hibernate.SQL", environment.getRequiredProperty("logging.level.org.hibernate.SQL"));

        return properties;
    }

    @Bean
    public LocalSessionFactoryBean entityManagerFactory()
    {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[]
        {
            "com.bookStrore.bookStorage.models"
        });
        sessionFactory.setHibernateProperties(hibernateProperties());
        
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager getTransactionManager()
    {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(entityManagerFactory().getObject());

        return transactionManager;
    }
}
