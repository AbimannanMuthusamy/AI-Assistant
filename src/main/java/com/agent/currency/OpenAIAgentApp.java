package main.java.com.agent.currency;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import io.github.cdimascio.dotenv.Dotenv;

public class OpenAIAgentApp {
    public static void main(String[] args) {

    	Dotenv dotenv = Dotenv.load();
        String openAIKey = dotenv.get("OPENAI_API_KEY");
        
    	// 1. Configure Claude (Anthropic)
        // Use "claude-3-5-sonnet-20240620" for the best agentic performance
        OpenAiChatModel model = OpenAiChatModel.builder()
                .apiKey(openAIKey)
                .modelName("claude-3-5-sonnet-20240620")
                .build();
        
        // 2. Build the Agent exactly as before
        Assistant agent = AiServices.builder(Assistant.class)
                .chatLanguageModel(model)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .tools(new CurrencyTool()) 
                .build();

		// 3. Run the agent
        String response = agent.chat("How many EUR is $125?");
        System.out.println("Claude Agent: " + response);
    }
}
