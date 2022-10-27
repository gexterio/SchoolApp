package ua.com.foxminded.sql_jdbc_school.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FileReader {
    public List<String> readFile(String file) {
        if (file == null || file.isEmpty() ||  file.isBlank()) {
            return Collections.emptyList();
        }
        if (getClass().getClassLoader().getResourceAsStream(file) == null) {
            return Collections.emptyList();
        }
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(file);
        ArrayList<String> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNext()) {
                list.add(scanner.nextLine());
            }
            return list;
        }
    }

    public InputStream readProperties (String fileName) {
        return getClass().getClassLoader().getResourceAsStream(fileName);

    }
}
