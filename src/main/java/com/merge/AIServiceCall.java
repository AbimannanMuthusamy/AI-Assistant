package com.merge;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class AIServiceCall {

	 private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";
	    private final String apiKey = "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";


	    public String callLLM(String systemPrompt, String userPrompt) throws Exception {

	        String jsonBody = """
	            {
	              "model": "gpt-4.1",
	              "temperature": 0,
	              "messages": [
	                { "role": "system", "content": "%s" },
	                { "role": "user", "content": "%s" }
	              ]
	            }
	            """.formatted(
	                    systemPrompt,
	                    userPrompt
	            );
	        
	        System.out.println("Request Body: " + jsonBody);
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(OPENAI_URL))
	                .header("Content-Type", "application/json")
	                .header("Authorization", "Bearer " + apiKey)
	                .POST(HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8))
	                .build();

	        HttpClient client = HttpClient.newHttpClient();

	        HttpResponse<String> response =
	                client.send(request, HttpResponse.BodyHandlers.ofString());

	        return response.body();
	    }

	    private static String escapeJson(String text) {
	        if (text == null) return "";
	        return text.replace("\\", "\\\\").replace("\"", "\\\"");
	    }
    
}


