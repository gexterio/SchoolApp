package ua.com.foxminded.sqlJdbcSchool.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileParser {
    private final static String STUDENT_NAME_PATTERN = "[A-Z][a-zA-Z]+";
    private final static String COURSE_PATTERN = "[A-Z][a-zA-Z ]+_.+";
    FileReader reader;

    @Autowired
    public FileParser() {
        this.reader = new FileReader();
    }

    public List<String> parseCourses(String fileName) {
        List<String> resultList = reader.readFile(fileName);
        return resultList.stream().filter(line -> line.matches(COURSE_PATTERN)).collect(Collectors.toList());
    }

    public List<String> parseStudent(String fileName) {
        List<String> resultList = reader.readFile(fileName);

        return resultList.stream().filter(line -> line.matches(STUDENT_NAME_PATTERN)).collect(Collectors.toList());
    }
}
