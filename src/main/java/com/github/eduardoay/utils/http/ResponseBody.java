package com.github.eduardoay.utils.http;

import java.util.HashMap;
import java.util.Map;

public class ResponseBody {

	private final String message;
	private final Object data;

	public ResponseBody(String message) {
		this.message = message;
		this.data = null;
	}

	public ResponseBody(String message, Object data) {
		this.message = message;
		this.data = data;
	}

	public ResponseBody(String message, Exception err) {
		Map<String, Object> res = new HashMap<>();
		res.put("error", err.getMessage());

		this.message = message;
		this.data = res;
	}

	public ResponseBody(String message, Exception err, boolean trace) {
		Map<String, Object> res = new HashMap<>();
		res.put("error", err.getMessage());

		if (trace) {
			res.put("trace", err.getStackTrace());
		}

		this.message = message;
		this.data = res;
	}

	public String getMessage() {
		return this.message;
	}

	public Object getData() {
		return this.data;
	}
}
