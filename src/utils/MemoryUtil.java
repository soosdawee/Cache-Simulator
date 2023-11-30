package utils;

import models.MyByte;

import java.util.*;

public class MemoryUtil {
    public static List<MyByte> createRandomMemory (Integer size) {
        List<MyByte> memory = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            memory.add(new MyByte(i, generateRandomAsciiChar()));
        }

        return memory;
    }

    private static Character generateRandomAsciiChar() {
        // ASCII range for printable characters: 32 to 126
        int asciiStart = 32;
        int asciiEnd = 126;

        Random random = new Random();
        int randomAsciiValue = random.nextInt(asciiEnd - asciiStart + 1) + asciiStart;

        // Convert the random ASCII value to a character
        Character randomAsciiChar = (char) randomAsciiValue;

        return randomAsciiChar;
    }
}
