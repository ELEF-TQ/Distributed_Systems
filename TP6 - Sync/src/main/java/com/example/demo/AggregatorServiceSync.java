package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AggregatorServiceSync {
    @Autowired
    private RestTemplate restTemplate;

    public AggregatedResponse getAggregatedResponseSync() {
        long start=System.currentTimeMillis();
        String result1 =restTemplate.getForObject("https://jsonplaceholder.typicode.com/photos", String.class);;
        String result2 =  restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts" , String.class);;
        String result3 = restTemplate.getForObject("https://jsonplaceholder.typicode.com/comments" , String.class);
        long end=System.currentTimeMillis();
        System.out.println("Execution time "+(end-start)+"ms");
        return  new AggregatedResponse(result1, result2, result3);


    }

}
