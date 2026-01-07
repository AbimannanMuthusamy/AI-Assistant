package main.java.com.survivorship;

public interface LLMService {
	
		String callLLM(String systemPrompt, String userPrompt, String modelName, String apiURL) throws Exception;
		
		String getAPIKey(String modelName) throws Exception;
		
}
