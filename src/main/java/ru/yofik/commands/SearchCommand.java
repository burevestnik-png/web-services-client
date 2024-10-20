package ru.yofik.commands;

import ru.itmo.yofik.webservices.back.api.ws.Filters;
import ru.itmo.yofik.webservices.back.api.ws.SearchRequest;
import ru.itmo.yofik.webservices.back.api.ws.Student;
import ru.itmo.yofik.webservices.back.api.ws.YofikWebService;
import ru.yofik.utils.ConsoleBuilder;
import ru.yofik.utils.ObjectParser;
import ru.yofik.utils.StudentPrinter;

import java.lang.reflect.Field;
import java.util.*;

public class SearchCommand implements Command {

    private final YofikWebService api;

    private final ConsoleBuilder consoleBuilder = new ConsoleBuilder(
            new ArrayList<ConsoleBuilder.Prompt>() {{
                add(new ConsoleBuilder.Prompt("byAgeMax", "Фильтровать по byAgeMax?", false));
                add(new ConsoleBuilder.Prompt("byAgeMin", "Фильтровать по byAgeMin?", false));
                add(new ConsoleBuilder.Prompt("byFirstName", "Фильтровать по byFirstName?", false));
                add(new ConsoleBuilder.Prompt("byHeightMax", "Фильтровать по byHeightMax?", false));
                add(new ConsoleBuilder.Prompt("byHeightMin", "Фильтровать по byHeightMin?", false));
                add(new ConsoleBuilder.Prompt("byLastName", "Фильтровать по byLastName?", false));
                add(new ConsoleBuilder.Prompt("byPatronymic", "Фильтровать по byPatronymic?", false));
            }}
    );

    public SearchCommand(YofikWebService api) {
        this.api = api;
    }

    @Override
    public void execute() {
        Map<String, String> answers = consoleBuilder.requestPrompts();
        System.out.println(answers);

        Filters filters = new Filters();
        Arrays.stream(filters.getClass().getDeclaredFields()).forEach(field -> {
            try {
                Field f = filters.getClass().getDeclaredField(field.getName());
                f.setAccessible(true);
                f.set(filters, ObjectParser.parse(answers.get(field.getName())));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });


        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setFilters(filters);
        List<Student> students = api.search(searchRequest);

        System.out.println("The API answered:");
        System.out.println("[");
        students.forEach(StudentPrinter::toJson);
        System.out.println("]");
    }
}
