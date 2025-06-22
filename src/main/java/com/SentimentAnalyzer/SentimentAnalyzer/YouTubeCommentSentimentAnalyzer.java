package com.SentimentAnalyzer.SentimentAnalyzer;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import com.google.gson.*;

public class YouTubeCommentSentimentAnalyzer {

    // TODO: Replace these with your real values
    private static final String API_KEY = "AIzaSyB1IyHi0KRQhodns2ScW7_mS5Ayvbl-q6E";  // <-- Replace
    private static final String VIDEO_ID = "yFyIBKK361E";           // <-- Replace

    public static void main(String[] args) {
        fetchAndAnalyzeComments(VIDEO_ID);
    }

    public static void fetchAndAnalyzeComments(String videoId) {
        String url = "https://www.googleapis.com/youtube/v3/commentThreads";

        try {
            HttpResponse<String> response = Unirest.get(url)
                    .queryString("part", "snippet")
                    .queryString("videoId", videoId)
                    .queryString("key", API_KEY)
                    .queryString("maxResults", 20)
                    .asString();

            if (response.getStatus() != 200) {
                System.out.println("API Error: " + response.getStatusText());
                return;
            }

            JsonObject jsonObject = JsonParser.parseString(response.getBody()).getAsJsonObject();
            JsonArray items = jsonObject.getAsJsonArray("items");

            for (JsonElement item : items) {
                JsonObject snippet = item.getAsJsonObject()
                        .getAsJsonObject("snippet")
                        .getAsJsonObject("topLevelComment")
                        .getAsJsonObject("snippet");

                String author = snippet.get("authorDisplayName").getAsString();
                String comment = snippet.get("textDisplay").getAsString();

                String sentiment = analyzeSentiment(comment);

                System.out.println("----------------------------------");
                System.out.println("👤 " + author);
                System.out.println("💬 " + comment);
                System.out.println("🧠 Sentiment: " + sentiment);
            }

        } catch (Exception e) {
            System.err.println("Exception while fetching comments: " + e.getMessage());
        }
    }

    // Basic rule-based sentiment analyzer
    public static String analyzeSentiment(String text) {
        text = text.toLowerCase();

        if (text.contains("love") || text.contains("great") || text.contains("awesome") || text.contains("excellent") || text.contains("nice"))
            return "Positive";

        if (text.contains("hate") || text.contains("worst") || text.contains("bad") || text.contains("terrible"))
            return "Negative";

        return "Neutral";
    }
}
