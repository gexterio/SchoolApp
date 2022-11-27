package ua.com.foxminded.sqlJdbcSchool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@ComponentScan("ua.com.foxminded.sqlJdbcSchool")
@PropertySource("testDBProperties.properties")
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

    @Bean
    public JdbcTemplate testJdbcTemplate() {
        return new JdbcTemplate(testDataSource());
    }
}
