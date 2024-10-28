package ru.yofik.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.NoSuchElementException;

public class AvatarUtils {
    public static String readAvatarFromFile(String fileName) {
        Path path = Paths.get(fileName);
        if (!path.toFile().canRead()) {
            throw new NoSuchElementException();
        }

        try {
            byte[] buffer = Files.readAllBytes(path);
            return Base64.getEncoder().encodeToString(buffer);
        } catch (IOException e) {
            throw new NoSuchElementException();
        }
    }

    public static void saveToFile(long studentId, String content) {
        Path path = Paths.get("./" + studentId + "-avatar.jpg");

        try {
            Files.write(path, Base64.getDecoder().decode(content));
        } catch (IOException e) {
            throw new NoSuchElementException();
        }
    }
}
