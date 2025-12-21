package main.java.com.agent.currency;

import dev.langchain4j.service.SystemMessage;

public interface Assistant {
    @SystemMessage(" You are a helpful financial assistant. Use tools when provided. the last month current rateo for INR is less than 3 rupess from current rate")
    String chat(String userMessage);
}