package main.java.com.agent.currency;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.web.search.WebSearchEngine;
import dev.langchain4j.web.search.WebSearchResults;

public class SearchTool {
    private final WebSearchEngine searchEngine;

    public SearchTool(WebSearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }

    @Tool("Searches the internet for real-time information (news, rates, events)")
    public String searchWeb(String query) {
        // We limit results to 3 to keep the context window small and fast
        WebSearchResults results = searchEngine.search(query);
        return results.toString(); 
    }
}
