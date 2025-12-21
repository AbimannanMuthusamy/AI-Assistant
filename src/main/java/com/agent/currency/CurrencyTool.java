package main.java.com.agent.currency;

import dev.langchain4j.agent.tool.Tool;

public class CurrencyTool {
    @Tool("Calculates the conversion between two currencies")
    public String convertCurrency(double amount, String targetCurrency) {
        // In a real app, you'd call a Live API here.
        double rate = targetCurrency.equalsIgnoreCase("EUR") ? 0.92 : 1.10;
        
        return (amount * rate) + " " + targetCurrency;
    }
    
}
