package com.example.kangarun.utils;

import java.util.HashMap;
import java.util.Map;

public class Tokenizer {
    // Tokenize the query and return null if invalid
    public static Map<String, String> tokenize(String input) {
        if (!input.contains("=")) {
            return null;
        }
        Map<String, String> tokens = new HashMap<>();
        String[] parts = input.split(";");
        for (String part : parts) {
            String[] keyValue = part.split("=");
            if (keyValue.length == 2) {
                tokens.put(keyValue[0].trim(), keyValue[1].trim());
            } else {
                return null;
            }
        }
        return tokens;
    }

}
