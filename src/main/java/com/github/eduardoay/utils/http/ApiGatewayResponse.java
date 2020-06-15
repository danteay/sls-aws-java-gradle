package com.github.eduardoay.utils.http;

import java.util.Collections;
import java.util.Map;

import com.github.eduardoay.utils.logging.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiGatewayResponse {

	private final int statusCode;
	private final String body;
	private final String message;
	private final Map<String, String> headers;
	private final boolean isBase64Encoded;
	private static final Logger LOG = new Logger(System.getenv("APP_NAME"));

	public ApiGatewayResponse(int statusCode, String body, Map<String, String> headers, boolean isBase64Encoded) {
		this.statusCode = statusCode;
		this.body = body;
		this.headers = headers;
		this.isBase64Encoded = isBase64Encoded;
		this.message = null;
	}

	public ApiGatewayResponse(int statusCode, ResponseBody body) {
		ObjectMapper mapper = new ObjectMapper();

		this.statusCode = statusCode;
		this.headers = Collections.emptyMap();
		this.isBase64Encoded = false;
		this.message = body.getMessage();

		try {
			this.body = mapper.writeValueAsString(body);
		} catch (JsonProcessingException e) {
			LOG.error("failed to serialize object", e);
			throw new RuntimeException(e);
		}
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getBody() {
		return body;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	// API Gateway expects the property to be called "isBase64Encoded" => isIs
	public boolean isIsBase64Encoded() {
		return isBase64Encoded;
	}

	public String getMessage() {
		return message;
	}

	public static ResponseBuilder builder() {
		return new ResponseBuilder();
	}
}
