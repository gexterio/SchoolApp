package ua.com.foxminded.sql_jdbc_school.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.sql_jdbc_school.menu.console_useractions.Exit;
import ua.com.foxminded.sql_jdbc_school.menu.console_useractions.UserOption;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;

@Component
public class Menu {
    @Autowired
    private Exit exit;
    private final Map<Integer, UserOption> userOptionsMap;

    @Autowired
    public Menu(Map<Integer, UserOption> userOptionsMap) {
        this.userOptionsMap = userOptionsMap;
    }

    public void run() {
        Integer exitKey = findExitCommandKey();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.println("Choose any option");
            int i = scanner.nextInt();
            userOptionsMap.get(i).execute();
            if (i == exitKey) {
                break;
            }
        }
    }

    private Integer findExitCommandKey() {
        Optional<Integer> exitKeyOptional = userOptionsMap.entrySet().stream()
                .filter(entry -> exit.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();
        return exitKeyOptional.orElseThrow(() -> new IllegalArgumentException("Exit command doesn't exist"));
    }

    private void printMenu() {
        System.out.println("—".repeat(30));
        IntStream.range(0, userOptionsMap.size()).forEach(
                index -> System.out.printf("[%d] — %s\n", index, userOptionsMap.get(index).getClass().getSimpleName()));
        System.out.println("—".repeat(30));
    }
}
