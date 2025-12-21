package main.java.com.agent.currency;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.web.search.tavily.TavilyWebSearchEngine;
import io.github.cdimascio.dotenv.Dotenv;

public class LocalAgentApp {
    public static void main(String[] args) {

    	Dotenv dotenv = Dotenv.load();
        String tavilyAPIKey = dotenv.get("TAVILY_API_KEY");
        
    	// 1. Configure OllamaChatModel
               
        OllamaChatModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("gpt-oss:120b-cloud")
                .build();
        
        
     // 2. Setup the Search Engine
        TavilyWebSearchEngine tavily = TavilyWebSearchEngine.builder()
                .apiKey(tavilyAPIKey)
                .build();

        // 3. Build the Agent exactly as before
        Assistant agent = AiServices.builder(Assistant.class)
                .chatLanguageModel(model)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .tools(new CurrencyTool(), new FileTool(), new SearchTool(tavily))
                .build();

		// 4. Run the agent
        
        String prompt = "Find the current exchange rate for Bitcoin to USD, " +
                "convert 0.5 BTC to USD, and save that to my log file.";
        String response = agent.chat(prompt);
        
        System.out.println("Response : " + response);
        
//        String response = agent.chat("How many EUR is $125?");
//        String response1 = agent.chat("convert the given EUR into INR");
//        String response2 = agent.chat("Thanks for converting to INR. can you compare with last month INR currency rate?");
//        System.out.println("Claude Agent: " + response);
//        System.out.println("Claude Agent: " + response1);
//        System.out.println("Claude Agent: " + response2);
    }
}
