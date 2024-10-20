package ru.yofik.utils;

import java.util.*;

public class ConsoleBuilder {

    private final List<Prompt> prompts;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleBuilder(List<Prompt> prompts) {
        this.prompts = prompts;
    }

    public Map<String, String> requestPrompts() {
        Map<String, String> answers = new HashMap<>();
        prompts.forEach(prompt -> {
            printPrompt(prompt);
            String input = scanner.nextLine();

            if (input.isEmpty() && prompt.isRequired) {
                while (input.isEmpty()) {
                    System.out.println("Введите обязательно поле -->");
                    printPrompt(prompt);
                    input = scanner.nextLine();
                }
            }

            answers.put(prompt.field, input);
        });
        return answers;
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

        public Prompt(String field, String text, boolean isRequired) {
            this.field = field;
            this.text = text;
            this.isRequired = isRequired;
        }
    }
}
