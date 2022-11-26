package ua.com.foxminded.sqlJdbcSchool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ua.com.foxminded.sqlJdbcSchool.menu.useractions.AddStudent;
import ua.com.foxminded.sqlJdbcSchool.menu.useractions.AddStudentToCourse;
import ua.com.foxminded.sqlJdbcSchool.menu.useractions.DeleteStudent;
import ua.com.foxminded.sqlJdbcSchool.menu.useractions.Exit;
import ua.com.foxminded.sqlJdbcSchool.menu.useractions.RemoveStudentFromCourse;
import ua.com.foxminded.sqlJdbcSchool.menu.useractions.SearchGroups;
import ua.com.foxminded.sqlJdbcSchool.menu.useractions.SearchStudentsInCourse;
import ua.com.foxminded.sqlJdbcSchool.menu.useractions.UserOption;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan("ua.com.foxminded.sqlJdbcSchool")
@PropertySource("properties.properties")
public class SpringConfig {

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

}
