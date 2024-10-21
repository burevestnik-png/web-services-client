package ru.yofik.commands;

import ru.itmo.yofik.webservices.back.api.ws.YofikWebService;
import ru.yofik.utils.ConsoleBuilder;
import ru.yofik.utils.ObjectParser;

import java.util.ArrayList;
import java.util.Map;

public class DeleteCommand implements Command {

    private final YofikWebService api;

    private final ConsoleBuilder consoleBuilder = new ConsoleBuilder(
            new ArrayList<ConsoleBuilder.Prompt>() {{
                add(new ConsoleBuilder.Prompt("id", "Введите id:", true, ConsoleBuilder.Prompt.Type.LONG));
            }}
    );

    public DeleteCommand(YofikWebService api) {
        this.api = api;
    }

    @Override
    public void execute() {
        Map<String, Object> answers = consoleBuilder.requestPrompts();
        System.out.println(answers);

        boolean isDeleted = api.delete((Long) answers.get("id"));
        System.out.println("The API answered isDeleted=" + isDeleted);
    }
}
