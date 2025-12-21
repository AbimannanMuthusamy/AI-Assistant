package main.java.com.agent.currency;

import dev.langchain4j.service.SystemMessage;

public interface Assistant {
    @SystemMessage("You are a helpful financial assistant. Use tools when provided.")
    String chat(String userMessage);
}