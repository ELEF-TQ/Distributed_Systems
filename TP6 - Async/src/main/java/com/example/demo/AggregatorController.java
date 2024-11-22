package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class AggregatorController {
    @Autowired
    private AggregatorServiceSync aggregatorServiceSync;
    @Autowired
    private AggregatorServiceAsync aggregatorServiceAsync;
    @GetMapping("/aggregateSync")
    public AggregatedResponse getAggregatedResponseSync() {
        return aggregatorServiceSync.getAggregatedResponseSync();
    }
    @GetMapping("/aggregateAsync")
    public AggregatedResponse getAggregatedResponseAsync() throws InterruptedException, ExecutionException {
        return aggregatorServiceAsync.getAggregatedResponseAsync();
    }
    }

