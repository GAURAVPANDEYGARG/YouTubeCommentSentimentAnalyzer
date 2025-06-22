package com.SentimentAnalyzer.SentimentAnalyzer.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SentimentAnalyzer.SentimentAnalyzer.Model.SentimentResult;
import com.SentimentAnalyzer.SentimentAnalyzer.Service.YouTubeCommentService;
import com.SentimentAnalyzer.SentimentAnalyzer.Utils.SentimentUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sentiment")
public class SentimentController {

    @Autowired
    private YouTubeCommentService commentService;

    @GetMapping("/youtube")
    public SentimentResult getYouTubeSentiment(@RequestParam String videoId) {
        return commentService.analyzeComments(videoId);
    }
}

