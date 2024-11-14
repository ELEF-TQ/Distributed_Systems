package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class AggregatorController {
    @Autowired
    private AggregatorServiceSync aggregatorServiceSync;
    @GetMapping("/aggregateSync")
    public AggregatedResponse getAggregatedResponseSync() {
        return aggregatorServiceSync.getAggregatedResponseSync();
    }
    }

