package ru.yofik.utils;

import java.util.*;

public class ConsoleBuilder {

    private final List<Prompt> prompts;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleBuilder(List<Prompt> prompts) {
        this.prompts = prompts;
    }

    public Map<String, Object> requestPrompts() {
        Map<String, Object> answers = new HashMap<>();
        for (Prompt prompt : prompts) {
            printPrompt(prompt);
            String input = scanner.nextLine();

            if (!prompt.isRequired && input.isEmpty()) {
                answers.put(prompt.field, null);
                continue;
            }

            if (prompt.type == Prompt.Type.INT) {
                while (input.isEmpty() || !ObjectParser.isInteger(input)) {
                    if (input.isEmpty()) {
                        System.out.println("Введите обязательно поле -->");
                        printPrompt(prompt);
                    } else {
                        System.out.println("Введите Integer -->");
                        printPrompt(prompt);
                    }
                    input = scanner.nextLine();
                }
                answers.put(prompt.field, Integer.valueOf(input));
            } else if (prompt.type == Prompt.Type.LONG) {
                while (input.isEmpty() || !ObjectParser.isLong(input)) {
                    if (input.isEmpty()) {
                        System.out.println("Введите обязательно поле -->");
                        printPrompt(prompt);
                    } else {
                        System.out.println("Введите Long -->");
                        printPrompt(prompt);
                    }
                    input = scanner.nextLine();
                }
                answers.put(prompt.field, Long.valueOf(input));
            } else {
                while (input.isEmpty()) {
                    System.out.println("Введите обязательно поле -->");
                    printPrompt(prompt);
                    input = scanner.nextLine();
                }
                answers.put(prompt.field, (input));
            }
        }
        return answers;
    }

    private String requestInput(String request, Prompt prompt) {
        System.out.println(request);
        printPrompt(prompt);
        return scanner.nextLine();
    }

    private void printPrompt(Prompt prompt) {
        System.out.println(new StringBuilder()
                .append(prompt.text)
                .append(prompt.isRequired ? "" : " (Press enter for skip)"));
    }

    public static class Prompt {
        public String field;
        public String text;
        public boolean isRequired;

        public Type type;

        public Prompt(String field, String text, boolean isRequired, Type type) {
            this.field = field;
            this.text = text;
            this.isRequired = isRequired;
            this.type = type;
        }

        public enum Type {
            INT, LONG, STRING
        }
    }
}
