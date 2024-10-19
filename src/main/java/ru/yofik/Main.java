package ru.yofik;

import ru.itmo.yofik.webservices.back.api.ws.SearchRequest;
import ru.itmo.yofik.webservices.back.api.ws.Student;
import ru.itmo.yofik.webservices.back.api.ws.YofikWebService;
import ru.itmo.yofik.webservices.back.api.ws.YofikWebService_Service;

import javax.xml.bind.annotation.XmlAccessType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        YofikWebService_Service serviceProvider = new YofikWebService_Service();
        YofikWebService service = serviceProvider.getYofikWebServicePort();

        SearchRequest request = new SearchRequest();
        List<Student> students = service.search(request);
        System.out.println(students.toString());
    }
}