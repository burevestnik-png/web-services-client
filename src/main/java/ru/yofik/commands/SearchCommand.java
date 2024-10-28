package ru.yofik.commands;

import ru.itmo.yofik.webservices.back.api.ws.*;
import ru.yofik.utils.ConsoleBuilder;
import ru.yofik.utils.ObjectParser;
import ru.yofik.utils.StudentPrinter;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class SearchCommand implements Command {

    private final YofikWebService api;
    private final ForkJoinPool forkJoinPool;

    private final ConsoleBuilder consoleBuilder = new ConsoleBuilder(
            new ArrayList<ConsoleBuilder.Prompt>() {{
                add(new ConsoleBuilder.Prompt("byAgeMax", "Фильтровать по byAgeMax?", false, ConsoleBuilder.Prompt.Type.INT));
                add(new ConsoleBuilder.Prompt("byAgeMin", "Фильтровать по byAgeMin?", false, ConsoleBuilder.Prompt.Type.INT));
                add(new ConsoleBuilder.Prompt("byFirstName", "Фильтровать по byFirstName?", false, ConsoleBuilder.Prompt.Type.STRING));
                add(new ConsoleBuilder.Prompt("byHeightMax", "Фильтровать по byHeightMax?", false, ConsoleBuilder.Prompt.Type.INT));
                add(new ConsoleBuilder.Prompt("byHeightMin", "Фильтровать по byHeightMin?", false, ConsoleBuilder.Prompt.Type.INT));
                add(new ConsoleBuilder.Prompt("byLastName", "Фильтровать по byLastName?", false, ConsoleBuilder.Prompt.Type.STRING));
                add(new ConsoleBuilder.Prompt("byPatronymic", "Фильтровать по byPatronymic?", false, ConsoleBuilder.Prompt.Type.STRING));
            }}
    );

    public SearchCommand(YofikWebService api, ForkJoinPool forkJoinPool) {
        this.api = api;
        this.forkJoinPool = forkJoinPool;
    }

    @Override
    public void execute() {
        Map<String, Object> answers = consoleBuilder.requestPrompts();
        System.out.println(answers);

        Filters filters = new Filters();
        filters.setByAgeMax((Integer) answers.get("byAgeMax"));
        filters.setByAgeMin((Integer) answers.get("byAgeMin"));
        filters.setByFirstName((String) answers.get("byFirstName"));
        filters.setByHeightMax((Integer) answers.get("byHeightMax"));
        filters.setByHeightMin((Integer) answers.get("byHeightMin"));
        filters.setByLastName((String) answers.get("byLastName"));
        filters.setByPatronymic((String) answers.get("byPatronymic"));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setFilters(filters);

        forkJoinPool.submit(() -> {
            List<Student> students = null;
            try {
                students = api.search(searchRequest);

                System.out.println("The API answered:");
                System.out.println("[");
                students.forEach(s -> System.out.println(StudentPrinter.toJson(s)));
                System.out.println("]");
            } catch (InvalidDataException e) {
                System.out.println("The API returned an exception: \"" + e.getFaultInfo().getMessage() + "\"");
            }
        });

    }
}
