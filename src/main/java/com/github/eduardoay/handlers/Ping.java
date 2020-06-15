package com.github.eduardoay.handlers;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.github.eduardoay.utils.http.ApiGatewayResponse;
import com.github.eduardoay.utils.http.ResponseBody;

public class Ping implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

  @Override
  public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
    return new ApiGatewayResponse(200, new ResponseBody("pong"));
  }

}