package ua.com.foxminded.sqljdbcschool.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ua.com.foxminded.sqljdbcschool.dao.hibernate.HibernateCourseDao;
import ua.com.foxminded.sqljdbcschool.dao.hibernate.HibernateStudentDao;
import ua.com.foxminded.sqljdbcschool.menu.consoleuseractions.*;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

@Configuration
@ComponentScan("ua.com.foxminded.sqljdbcschool")
@PropertySource("classpath:properties.properties")
@EnableTransactionManagement(proxyTargetClass = true)
public class SpringConfig {

    private final Environment environment;

    @Autowired
    public SpringConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public Map<Integer, UserOption> userOptionMap(
            AddStudent addStudent,
            AddStudentToCourse addStudentToCourse,
            DeleteStudent deleteStudent,
            RemoveStudentFromCourse removeStudentFromCourse,
            SearchGroups searchGroups,
            SearchStudentsInCourse searchStudentsInCourse,
            Exit exit
    ) {
        Map<Integer, UserOption> map = new HashMap<>();
        map.put(0, addStudent);
        map.put(1, addStudentToCourse);
        map.put(2, deleteStudent);
        map.put(3, removeStudentFromCourse);
        map.put(4, searchGroups);
        map.put(5, searchStudentsInCourse);
        map.put(6, exit);
        return map;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("driver")));
        dataSource.setUrl(environment.getProperty("url"));
        dataSource.setUsername(environment.getProperty("user"));
        dataSource.setPassword(environment.getProperty("password"));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
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
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("ua.com.foxminded.sqljdbcschool.dto");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
}
