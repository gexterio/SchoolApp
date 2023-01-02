package ua.com.foxminded.sqljdbcschool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ua.com.foxminded.sqljdbcschool.dao.hibernate.HibernateCourseDao;
import ua.com.foxminded.sqljdbcschool.dao.hibernate.HibernateStudentDao;
import ua.com.foxminded.sqljdbcschool.util.DTOInputValidator;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
//@SpringJUnitConfig
//@ComponentScan("ua.com.foxminded.sqljdbcschool")
@PropertySource("classpath:testDBProperties.properties")
@EnableTransactionManagement(proxyTargetClass = true)

public class TestSpringConfig {
    private final Environment environment;


    @Autowired
    public TestSpringConfig(Environment environment) {
        this.environment = environment;
    }


    @Bean
    public DataSource testDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("JDBC_DRIVER")));
        dataSource.setUrl(environment.getProperty("JDBC_URL"));
        dataSource.setUsername(environment.getProperty("USER"));
        dataSource.setPassword(environment.getProperty("PASSWORD"));
        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.jdbc.batch_size", environment.getRequiredProperty("hibernate.jdbc.batch_size"));
        properties.put("hibernate.generate_statistics", environment.getRequiredProperty("hibernate.generate_statistics"));
        return properties;
    }

    @Bean
    public LocalSessionFactoryBean testSessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(testDataSource());
        sessionFactory.setPackagesToScan("ua.com.foxminded.sqljdbcschool.dto");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager testHibernateTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(testSessionFactory().getObject());
        return transactionManager;
    }

    @Bean
    public HibernateCourseDao hibernateCourseDao() {
        return new HibernateCourseDao(testSessionFactory().getObject(), new DTOInputValidator());
    }
    @Bean
    public HibernateStudentDao hibernateStudentDao() {
        return new HibernateStudentDao(testSessionFactory().getObject());
    }
}
