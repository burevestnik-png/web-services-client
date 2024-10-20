package ru.yofik.utils;

import ru.itmo.yofik.webservices.back.api.ws.Student;

public class StudentPrinter {
    public static String toJson(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"id\": ").append(student.getId()).append(",\n");
        sb.append("  \"age\": ").append(student.getAge()).append(",\n");
        sb.append("  \"firstName\": \"").append(student.getFirstName()).append("\",\n");
        sb.append("  \"height\": ").append(student.getHeight()).append(",\n");
        sb.append("  \"lastName\": \"").append(student.getLastName()).append("\",\n");
        sb.append("  \"patronymic\": \"").append(student.getPatronymic()).append("\"\n");
        sb.append("}");
        return sb.toString();
    }
}
