//package ua.com.foxminded.sqljdbcschool;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//import org.springframework.mock.web.MockServletContext;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//import javax.servlet.ServletContext;
//
//@WebAppConfiguration
//@ComponentScan("ua.com.foxminded.sqljdbcschool")
//@PropertySource("classpath:testDBProperties.properties")
//public class TestSpringConfig {
//
//    private final Environment environment;
//    ServletContext servletContext = new MockServletContext();
//
//
//    @Autowired
//    public TestSpringConfig(Environment environment) {
//        this.environment = environment;
//    }
//
//
////    @Bean
////    public DataSource testDataSource() {
////        DriverManagerDataSource dataSource = new DriverManagerDataSource();
////        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("JDBC_DRIVER")));
////        dataSource.setUrl(environment.getProperty("JDBC_URL"));
////        dataSource.setUsername(environment.getProperty("USER"));
////        dataSource.setPassword(environment.getProperty("PASSWORD"));
////        return dataSource;
////    }
//
//
//}
