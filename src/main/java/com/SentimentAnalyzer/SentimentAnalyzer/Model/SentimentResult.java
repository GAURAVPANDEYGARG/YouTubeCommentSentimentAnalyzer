package com.SentimentAnalyzer.SentimentAnalyzer.Model;



import java.util.List;

public class SentimentResult {
    public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public int getPositive() {
		return positive;
	}

	public void setPositive(int positive) {
		this.positive = positive;
	}

	public int getNegative() {
		return negative;
	}

	public void setNegative(int negative) {
		this.negative = negative;
	}

	public int getNeutral() {
		return neutral;
	}

	public void setNeutral(int neutral) {
		this.neutral = neutral;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}

	private String videoId;
    private int positive;
    private int negative;
    private int neutral;
    private List<String> comments;

    public SentimentResult(String videoId, int positive, int negative, int neutral, List<String> comments) {
        this.videoId = videoId;
        this.positive = positive;
        this.negative = negative;
        this.neutral = neutral;
        this.comments = comments;
    }

    // Getters and Setters
}
