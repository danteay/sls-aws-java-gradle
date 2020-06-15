package com.github.eduardoay.utils.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Request {

    public static Object bind(final Map<String, Object> req, final Class cls) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(req.get("body").toString().getBytes(StandardCharsets.UTF_8), cls);
    }
}
