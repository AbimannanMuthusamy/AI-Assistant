package main.java.com.survivorship;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class OpenAIService implements LLMService {

	@Override
	public String callLLM(String systemPrompt, String userPrompt, String modelName, String apiURL) throws Exception {
		java.net.http.HttpResponse<String> response = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			// Build JSON object, NOT manual string
			ObjectNode root = mapper.createObjectNode();
			root.put("model", modelName);
			root.put("temperature", 0);
			ArrayNode messages = root.putArray("messages");

			ObjectNode sysMsg = messages.addObject();
			sysMsg.put("role", "system");
			sysMsg.put("content", systemPrompt); // TOON string, raw. Jackson will escape.

			ObjectNode userMsg = messages.addObject();
			userMsg.put("role", "user");
			userMsg.put("content", userPrompt); // TOON string, raw. Jackson will escape.

			String jsonBody = mapper.writeValueAsString(root);

			// (Optional) Log to verify it�s valid JSON
			System.out.println("JSON body being sent:\n" + jsonBody);

			// Send with JDK HttpClient (you can adapt to Apache)
			java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();

			java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder().uri(java.net.URI.create(apiURL))
					.header("Content-Type", "application/json")
					.header("Authorization", "Bearer " + getAPIKey(modelName))
					.POST(java.net.http.HttpRequest.BodyPublishers.ofString(jsonBody)).build();

			response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
			parseResponse(response.body());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response.body();

	}

	@Override
	public String getAPIKey(String modelName) throws Exception {
		// TODO Auto-generated method stub
		String apiKey = "";
		return apiKey;
	}

	private void parseResponse(String response) {
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			// 1. Read the main JSON string into a JsonNode object
			JsonNode rootNode = objectMapper.readTree(response);

			System.out.println(response);

			// 2. Navigate to the "choices" array
			JsonNode choicesNode = rootNode.path("choices");

			// 3. Check if "choices" is an array and has elements
			if (choicesNode.isArray() && choicesNode.size() > 0) {
				// We'll take the first element (index 0) of the "choices" array
				JsonNode firstChoice = choicesNode.get(0);

				// 4. Navigate to the "message" object
				JsonNode messageNode = firstChoice.path("message");

				// 5. Navigate to the "content" field and get its string value
				String contentString = messageNode.path("content").asText();

				System.out.println("--- Extracted Content String ---");
				System.out.println(contentString);

				// BONUS: The 'content' itself is another JSON string, we can parse it too
				System.out.println("\n--- Parsed Content JSON (Bonus) ---");
				JsonNode contentJson = objectMapper.readTree(contentString);
//	                System.out.println("comparator_record_guid : "+contentJson.path("comparator_record_guid").asText());
			} else {
				System.out.println("Error: 'choices' array is missing or empty.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
