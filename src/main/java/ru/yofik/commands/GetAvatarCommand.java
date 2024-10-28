package ru.yofik.commands;

import ru.itmo.yofik.webservices.back.api.ws.NotFoundException;
import ru.itmo.yofik.webservices.back.api.ws.YofikWebService;
import ru.yofik.utils.AvatarUtils;
import ru.yofik.utils.ConsoleBuilder;

import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;

public class GetAvatarCommand implements Command {
    private final YofikWebService api;

    private final ConsoleBuilder consoleBuilder = new ConsoleBuilder(
            new ArrayList<ConsoleBuilder.Prompt>() {{
                add(new ConsoleBuilder.Prompt("id", "Введите id:", true, ConsoleBuilder.Prompt.Type.LONG));
            }}
    );

    public GetAvatarCommand(YofikWebService api) {
        this.api = api;
    }

    @Override
    public void execute() {
        Map<String, Object> answers = consoleBuilder.requestPrompts();
        System.out.println(answers);

        try {
            long id = (Long) answers.get("id");
            String content = api.getAvatar(id);
            AvatarUtils.saveToFile(id, content);

            System.out.println("Avatar has been saved to ./" + id + "-avatar.jpg");
        } catch (NotFoundException e) {
            System.out.println("The API returned an exception: \"" + e.getFaultInfo().getMessage() + "\"");
        } catch (NoSuchElementException | InvalidPathException e) {
            System.out.println("An error occurred while trying to save avatar");
        }
    }
}
