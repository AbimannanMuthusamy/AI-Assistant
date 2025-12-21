package main.java.com.agent.currency;

import dev.langchain4j.agent.tool.Tool;

public class CurrencyTool {
    @Tool("Calculates the conversion between two currencies")
    public String convertCurrency(double amount, String targetCurrency) {
        // In a real app, you'd call a Live API here.
        double rate = getMockExchangeRate(targetCurrency);
        
        return (amount * rate) + " " + targetCurrency; 
    }
    
    private double getMockExchangeRate(String targetCurrency) {
		// Mock exchange rates for demonstration purposes
		switch (targetCurrency.toUpperCase()) {
			case "EUR":
				return 0.92; // Example rate for USD to EUR
			case "INR":
				return 82.50; // Example rate for USD to INR
			default:
				return 1.0; // Default to 1:1 for unknown currencies
		}
	}
    
}
