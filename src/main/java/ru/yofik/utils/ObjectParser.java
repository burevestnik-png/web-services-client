package ru.yofik.utils;

public class ObjectParser {
    public static Object parse(String raw) {
        if (raw.isEmpty()) {
            return null;
        }
        if (isInteger(raw)) {
            return Integer.parseInt(raw);
        }
        return raw;
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
