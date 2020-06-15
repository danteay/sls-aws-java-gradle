package com.github.eduardoay.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import com.github.eduardoay.utils.http.ApiGatewayResponse;

import org.junit.jupiter.api.Test;

public class PingTest {

  @Test
  public void handleRequestTest() {
    Ping handler = new Ping();

    Map<String, Object> req = new HashMap<>();

    ApiGatewayResponse res = handler.handleRequest(req, null);

    assertEquals(200, res.getStatusCode());
    assertEquals("pong", res.getMessage());
  }
}