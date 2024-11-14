package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@EnableAsync // Enable async processing in Spring Boot
@Service
public class AggregatorServiceAsync {

    @Autowired
    private RestTemplate restTemplate;

    @Async("taskExecutor")
    public CompletableFuture<String> fetchPhotos() {
        return CompletableFuture.completedFuture(
                restTemplate.getForObject("https://jsonplaceholder.typicode.com/photos", String.class)
        );
    }

    @Async("taskExecutor")
    public CompletableFuture<String> fetchPosts() {
        return CompletableFuture.completedFuture(
                restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts", String.class)
        );
    }

    @Async("taskExecutor")
    public CompletableFuture<String> fetchComments() {
        return CompletableFuture.completedFuture(
                restTemplate.getForObject("https://jsonplaceholder.typicode.com/comments", String.class)
        );
    }


    public AggregatedResponse getAggregatedResponseAsync() {
        long start = System.currentTimeMillis();

        CompletableFuture<String> photosFuture = fetchPhotos();
        CompletableFuture<String> postsFuture = fetchPosts();
        CompletableFuture<String> commentsFuture = fetchComments();

        // Wait for all the async calls to complete
        CompletableFuture.allOf(photosFuture, postsFuture, commentsFuture).join();

        String result1 = photosFuture.join();
        String result2 = postsFuture.join();
        String result3 = commentsFuture.join();

        long end = System.currentTimeMillis();
        System.out.println("Execution time " + (end - start) + "ms");

        return new AggregatedResponse(result1, result2, result3);
    }
}
