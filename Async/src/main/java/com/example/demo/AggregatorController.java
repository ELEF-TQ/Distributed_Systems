package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class AggregatorController {
    @Autowired
    private AggregatorServiceAsync aggregatorServiceAsync;
    @GetMapping("/aggregateAsync")
    public AggregatedResponse getAggregatedResponseAsync() {
        return aggregatorServiceAsync.getAggregatedResponseAsync();
    }
    }

