package com.example;

import javax.naming.Context;
import java.util.Map;

public class LambdaHandler {

    private static final String MESSAGE_KEY = "message";

    public String handleRequest(
            Map<String, Object> request,
            Context context) {

        String message = (String) request.get(MESSAGE_KEY);
        return "Processed message: " + message;
    }
}
