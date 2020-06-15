package com.github.eduardoay.utils.http;

import com.github.eduardoay.utils.logging.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

public class ResponseBuilder {

    private static final Logger LOG = new Logger(System.getenv("APP_NAME"));

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private int statusCode = 200;
    private Map<String, String> headers = Collections.emptyMap();
    private String rawBody;
    private ResponseBody objectBody;
    private byte[] binaryBody;
    private boolean base64Encoded;

    public ResponseBuilder setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public ResponseBuilder setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    /**
     * Builds the {@link ApiGatewayResponse} using the passed raw body string.
     */
    public ResponseBuilder setRawBody(String rawBody) {
        this.rawBody = rawBody;
        return this;
    }

    /**
     * Builds the {@link ApiGatewayResponse} using the passed object body
     * converted to JSON.
     */
    public ResponseBuilder setBody(ResponseBody objectBody) {
        this.objectBody = objectBody;
        return this;
    }

    /**
     * Builds the {@link ApiGatewayResponse} using the passed binary body
     * encoded as base64. {@link #setBase64Encoded(boolean)
     * setBase64Encoded(true)} will be in invoked automatically.
     */
    public ResponseBuilder setBinaryBody(byte[] binaryBody) {
        this.binaryBody = binaryBody;
        setBase64Encoded(true);
        return this;
    }

    /**
     * A binary or rather a base64encoded responses requires
     * <ol>
     * <li>"Binary Media Types" to be configured in API Gateway
     * <li>a request with an "Accept" header set to one of the "Binary Media
     * Types"
     * </ol>
     */
    public ResponseBuilder setBase64Encoded(boolean base64Encoded) {
        this.base64Encoded = base64Encoded;
        return this;
    }

    public ApiGatewayResponse build() {
        String body = null;
        if (rawBody != null) {
            body = rawBody;
        } else if (objectBody != null) {
            try {
                body = objectMapper.writeValueAsString(objectBody);
            } catch (JsonProcessingException e) {
                LOG.error("failed to serialize object", e);
                throw new RuntimeException(e);
            }
        } else if (binaryBody != null) {
            body = new String(Base64.getEncoder().encode(binaryBody), StandardCharsets.UTF_8);
        }
        return new ApiGatewayResponse(statusCode, body, headers, base64Encoded);
    }
}
