package com.example.kangarun.utils;

import com.example.kangarun.activity.MainActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    private static final String GENDER_REGEX = "^[mfo]$";

    // Parse the query and return null if invalid
    public static Map<String, String> parse(Map<String, String> tokens) throws IllegalArgumentException {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, String> entry : tokens.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key == null || value == null){
                return null;
            }
            switch (key) {
                case "name":
                    result.put("username", value);
                    break;
                case "email":
                    result.put("email", value);
                    break;
                case "gender":
                    if (validateGender(value)) {
                        result.put("gender", value);
                    } else {
                        return null;
                    }
                    break;
                default:
                    return null;
            }
        }

        if (result.isEmpty()) {
            return null;
        }

        return result;
    }

    private static boolean validateEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    private static boolean validateGender(String gender) {
        return gender.matches(GENDER_REGEX);
    }

    public static Map<String, String> processQuery(String query) {
        Map<String, String> tokens = Tokenizer.tokenize(query);
        if (tokens == null) {
            return null;
        } else {
            return Parser.parse(tokens);
        }
    }
}
