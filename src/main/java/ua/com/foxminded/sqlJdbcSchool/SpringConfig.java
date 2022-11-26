package ua.com.foxminded.sqlJdbcSchool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.com.foxminded.sqlJdbcSchool.menu.useractions.AddStudent;
import ua.com.foxminded.sqlJdbcSchool.menu.useractions.AddStudentToCourse;
import ua.com.foxminded.sqlJdbcSchool.menu.useractions.DeleteStudent;
import ua.com.foxminded.sqlJdbcSchool.menu.useractions.Exit;
import ua.com.foxminded.sqlJdbcSchool.menu.useractions.RemoveStudentFromCourse;
import ua.com.foxminded.sqlJdbcSchool.menu.useractions.SearchGroups;
import ua.com.foxminded.sqlJdbcSchool.menu.useractions.SearchStudentsInCourse;
import ua.com.foxminded.sqlJdbcSchool.menu.useractions.UserOption;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@ComponentScan("ua.com.foxminded.sqlJdbcSchool")
@PropertySource("classpath:properties.properties")
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

}
