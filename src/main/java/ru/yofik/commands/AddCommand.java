package ru.yofik.commands;

import ru.itmo.yofik.webservices.back.api.ws.CreateRequest;
import ru.itmo.yofik.webservices.back.api.ws.CreateResponse;
import ru.itmo.yofik.webservices.back.api.ws.YofikWebService;
import ru.yofik.utils.ConsoleBuilder;

import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class AddCommand implements Command {

    private final YofikWebService api;

    private final ConsoleBuilder consoleBuilder = new ConsoleBuilder(
            new ArrayList<ConsoleBuilder.Prompt>() {{
                add(new ConsoleBuilder.Prompt("age", "Введите age:", true, ConsoleBuilder.Prompt.Type.INT));
                add(new ConsoleBuilder.Prompt("firstName", "Введите firstName:", true, ConsoleBuilder.Prompt.Type.STRING));
                add(new ConsoleBuilder.Prompt("height", "Введите height:", true, ConsoleBuilder.Prompt.Type.INT));
                add(new ConsoleBuilder.Prompt("lastName", "Введите lastName:", true, ConsoleBuilder.Prompt.Type.STRING));
                add(new ConsoleBuilder.Prompt("patronymic", "Введите patronymic:", true, ConsoleBuilder.Prompt.Type.STRING));
            }}
    );

    public AddCommand(YofikWebService api) {
        this.api = api;
    }

    @Override
    public void execute() {
        Map<String, Object> answers = consoleBuilder.requestPrompts();
        System.out.println(answers);

        CreateRequest request = new CreateRequest();
        request.setAge((Integer) answers.get("age"));
        request.setHeight((Integer) answers.get("height"));
        request.setFirstName((String) answers.get("firstName"));
        request.setLastName((String) answers.get("lastName"));
        request.setPatronymic((String) answers.get("patronymic"));

        api.createAsync(request, new AddCommandAsyncHandler());
    }
}

class AddCommandAsyncHandler implements AsyncHandler<CreateResponse> {

    @Override
    public void handleResponse(Response<CreateResponse> response) {
        try {
            System.out.println("The API answered code=" + response.get().getReturn());
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error during execution request");
        }
    }
}
