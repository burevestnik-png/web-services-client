package ru.yofik;

import ru.itmo.yofik.webservices.back.api.ws.YofikWebService;
import ru.itmo.yofik.webservices.back.api.ws.YofikWebService_Service;
import ru.yofik.commands.Command;
import ru.yofik.commands.SearchCommand;

import java.util.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final YofikWebService_Service serviceProvider = new YofikWebService_Service();
    private static final YofikWebService service = serviceProvider.getYofikWebServicePort();

    private static Map<String, Command> commands = new HashMap<String, Command>() {{
        put("search", new SearchCommand(service));
    }};

    public static void main(String[] args) {
        System.out.println("Welcome for <YofikWebService> client, available commands:");
        commands.forEach((k, v) -> {
            System.out.println("--- " + k + "\n");
        });

        while (true) {
            String nextLine = scanner.nextLine();

            String[] splittedInput = nextLine.split(" ");
            if (splittedInput.length < 1) {
                System.out.println("Please enter command");
                continue;
            }

            String command = splittedInput[0];
            if (!commands.containsKey(command)) {
                System.out.println("Such command doesn't exist");
                continue;
            }

            commands.get(command).execute();
        }
    }
}