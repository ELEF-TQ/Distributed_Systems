package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@Service
public class AggregatorServiceAsync {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Executor asyncExecutor;

    public AggregatedResponse getAggregatedResponseAsync() throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();

        CompletableFuture<String> result1Future = CompletableFuture.supplyAsync(() ->
                restTemplate.getForObject("https://jsonplaceholder.typicode.com/photos", String.class), asyncExecutor);
        CompletableFuture<String> result2Future = CompletableFuture.supplyAsync(() ->
                restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts", String.class), asyncExecutor);
        CompletableFuture<String> result3Future = CompletableFuture.supplyAsync(() ->
                restTemplate.getForObject("https://jsonplaceholder.typicode.com/comments", String.class), asyncExecutor);

        String result1 = result1Future.get();
        String result2 = result2Future.get();
        String result3 = result3Future.get();

        long end = System.currentTimeMillis();
        System.out.println("Execution time " + (end - start) + "ms");

        return new AggregatedResponse(result1, result2, result3);
    }
}