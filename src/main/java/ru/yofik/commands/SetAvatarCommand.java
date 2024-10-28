package ru.yofik.commands;

import ru.itmo.yofik.webservices.back.api.ws.NotFoundException;
import ru.itmo.yofik.webservices.back.api.ws.SetAvatarRequest;
import ru.itmo.yofik.webservices.back.api.ws.YofikWebService;
import ru.yofik.utils.AvatarUtils;
import ru.yofik.utils.ConsoleBuilder;

import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;

public class SetAvatarCommand implements Command {
    private final YofikWebService api;

    private final ConsoleBuilder consoleBuilder = new ConsoleBuilder(
            new ArrayList<ConsoleBuilder.Prompt>() {{
                add(new ConsoleBuilder.Prompt("id", "Введите id:", true, ConsoleBuilder.Prompt.Type.LONG));
                add(new ConsoleBuilder.Prompt("file", "Введите путь к файлу (абсолютный):", true, ConsoleBuilder.Prompt.Type.STRING));
            }}
    );

    public SetAvatarCommand(YofikWebService api) {
        this.api = api;
    }

    @Override
    public void execute() {
        Map<String, Object> answers = consoleBuilder.requestPrompts();
        System.out.println(answers);

        SetAvatarRequest request = new SetAvatarRequest();
        request.setStudentId((Long) answers.get("id"));

        try {
            request.setContent(AvatarUtils.readAvatarFromFile((String) answers.get("file")));
        } catch (NoSuchElementException | InvalidPathException e) {
            System.out.println("An error occurred while reading avatar file");
            return;
        }

        boolean status;
        try {
            status = api.setAvatar(request);
            System.out.println("The API answered code=" + status);
        } catch (NotFoundException e) {
            System.out.println("The API returned an exception: \"" + e.getFaultInfo().getMessage() + "\"");
        }
    }
}
