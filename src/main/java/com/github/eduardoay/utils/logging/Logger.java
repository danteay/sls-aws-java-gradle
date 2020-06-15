package com.github.eduardoay.utils.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Logger {
    private final Map<String, Object> extras;
    private final String name;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");

    public static final String DEBUG = "DEBUG";
    public static final String INFO = "INFO";
    public static final String WARNING = "WARNING";
    public static final String ERROR = "ERROR";

    public Logger(String name) {
        this.name = name;
        this.extras = new HashMap<>();
    }

    public Logger addField(String key, Object value) {
        this.extras.put(key, value);
        return this;
    }

    public Logger addFields(Map<String, Object> fields) {
        this.extras.putAll(fields);
        return this;
    }

    public void debug(String message) {
        log(DEBUG, message);
    }

    public void info(String message) {
        log(INFO, message);
    }

    public void warning(String message) {
        log(WARNING, message);
    }

    public void warning(String message, Exception err) {
        log(WARNING, message, err);
    }

    public void error(String message) {
        log(ERROR, message);
    }

    public void error(String message, Exception err) {
        log(ERROR, message, err);
    }

    public void log(String level, String message) {
        Map<String, Object> data = getBaseJson(level, message);

        String json = buildJson(data);

        System.out.println(json);
        extras.clear();
    }

    public void log(String level, String message, Exception err) {
        Map<String, Object> data = getBaseJson(level, message);
        data.put("error", err.getMessage());
        data.put("trace", getStringTrace(err));

        String json = buildJson(data);

        System.out.println(json);
        extras.clear();

    }

    private Map<String, Object> getBaseJson(String level, String message) {

        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", getTimestamp());
        data.put("level", level);
        data.put("name", name);
        data.put("message", message);

        data.putAll(extras);

        return data;
    }

    private String buildJson(Map<String, Object> data) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return plainLoggerError(e);
        }
    }

    private String getTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return sdf.format(timestamp);
    }

    private String getStringTrace(Exception err) {
        StackTraceElement[] trace = err.getStackTrace();
        return Arrays.toString(trace);
    }

    private String plainLoggerError(Exception err) {
        return "{" +
                "\"timestamp\": \"" + getTimestamp() + "\"" +
                "\"level\": \"ERROR\"" +
                "\"name\": \"" + name + "\"" +
                "\"message\": \"logger parse exception\"" +
                "\"error\": \"" + err.getMessage() + "\"" +
                "\"trace\": \"" + getStringTrace(err) + "\"" +
                "}";
    }
}
