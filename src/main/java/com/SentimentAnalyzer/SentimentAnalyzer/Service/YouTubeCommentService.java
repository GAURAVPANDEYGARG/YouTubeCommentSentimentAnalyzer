package com.SentimentAnalyzer.SentimentAnalyzer.Service;

import com.SentimentAnalyzer.SentimentAnalyzer.Model.SentimentResult;
import com.SentimentAnalyzer.SentimentAnalyzer.Utils.SentimentUtils;
import com.google.gson.*;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class YouTubeCommentService {

	@Value("${youtube.api.key}")
    private String apiKey;

    public SentimentResult analyzeComments(String videoId) {
        String url = "https://www.googleapis.com/youtube/v3/commentThreads";
        List<String> comments = new ArrayList<>();

        int pos = 0, neg = 0, neu = 0;

        try {
            HttpResponse<String> response = Unirest.get(url)
                    .queryString("part", "snippet")
                    .queryString("videoId", videoId)
                    .queryString("key", apiKey)
                    .queryString("maxResults", 20)
                    .asString();
            System.out.println("API response: " + response.getBody());

            JsonObject json = JsonParser.parseString(response.getBody()).getAsJsonObject();
            

         // ✅ Check if response has an error
         if (json.has("error")) {
             String message = json.getAsJsonObject("error").get("message").getAsString();
             System.err.println("API Error: " + message);
             return new SentimentResult(videoId, 0, 0, 0, List.of("Error: " + message));
         }

         // ✅ Check if 'items' exists before using it
         if (!json.has("items") || json.getAsJsonArray("items") == null) {
             System.err.println("No 'items' in response — either video not found or no comments.");
             return new SentimentResult(videoId, 0, 0, 0, List.of("No comments or invalid video."));
         }
            JsonArray items = json.getAsJsonArray("items");

            for (JsonElement item : items) {
                JsonObject snippet = item.getAsJsonObject()
                        .getAsJsonObject("snippet")
                        .getAsJsonObject("topLevelComment")
                        .getAsJsonObject("snippet");

                String comment = snippet.get("textDisplay").getAsString();
                comments.add(comment);
                String sentiment = SentimentUtils.analyzeSentiment(comment);

                switch (sentiment) {
                    case "Positive" -> pos++;
                    case "Negative" -> neg++;
                    default -> neu++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new SentimentResult(videoId, pos, neg, neu, comments);
    }
}
