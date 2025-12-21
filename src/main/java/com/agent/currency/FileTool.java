package main.java.com.agent.currency;

import dev.langchain4j.agent.tool.Tool;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.IOException;

public class FileTool {
    @Tool("Saves a string message to a local file named 'conversions.txt'")
    public void saveToFile(String content) {
        try {
            String logEntry = "\n[Log]: " + content;
            Files.write(
                Paths.get("C:\\Users\\abimannan.m\\Desktop\\Desktop\\FileTool\\conversions.txt"), 
                logEntry.getBytes(), 
                StandardOpenOption.CREATE, 
                StandardOpenOption.APPEND
            );
            System.out.println(">> System: Data successfully written to file.");
        } catch (IOException e) {
           e.getMessage();
        }
    }
}