package main.java.com.merge;

public class MergeMain {

	public static void main(String[] args) {

		try {
			MergePrompt mergePrompt = new MergePrompt();
			OpenAIService aiServiceCall = new OpenAIService();
			
			String systemPrompt = MergePrompt.SYSTEM_PROMPT;
			String userPrompt = MergePrompt.USER_PROMPT;
			
			 String modelName = "gpt-4.1";
			 String apiURL = "https://api.openai.com/v1/chat/completions";
			
			String response = aiServiceCall.callLLM( systemPrompt, userPrompt, modelName, apiURL);
			//System.out.println("Response from LLM:");
			//System.out.println(response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
