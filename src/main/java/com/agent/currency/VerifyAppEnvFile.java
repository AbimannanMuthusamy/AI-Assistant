package main.java.com.agent.currency;

import io.github.cdimascio.dotenv.Dotenv;

public class VerifyAppEnvFile {
	public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String anthropicapiKey = dotenv.get("ANTHROPIC_API_KEY");
        String openaiapiKey = dotenv.get("OPENAI_API_KEY");
        
        System.out.println("anthropicapi Key : " + anthropicapiKey);
        System.out.println("openaiapi Key: " + openaiapiKey);
    }
}
