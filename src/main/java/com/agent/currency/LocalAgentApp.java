package main.java.com.agent.currency;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;

public class LocalAgentApp {
    public static void main(String[] args) {

        
    	// 1. Configure OllamaChatModel
               
        OllamaChatModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("gpt-oss:120b-cloud")
                .build();

        // 2. Build the Agent exactly as before
        Assistant agent = AiServices.builder(Assistant.class)
                .chatLanguageModel(model)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .tools(new CurrencyTool()) 
                .build();

		// 3. Run the agent
        String response = agent.chat("How many EUR is $125?");
        String response1 = agent.chat("convert the given EUR into INR");
        System.out.println("Claude Agent: " + response);
        System.out.println("Claude Agent: " + response1);
    }
}
