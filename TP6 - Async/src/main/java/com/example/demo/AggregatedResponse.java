package com.example.demo;
public class AggregatedResponse {
    private String S1;
    private String S2;
    private String S3;
    public AggregatedResponse(String s1, String s2, String s3) {
        S1 = s1;
        S2 = s2;
        S3 = s3;
    }
    public String getS1() {
        return S1;
    }
    public String getS2() {
        return S2;
    }
    public String getS3() {
        return S3;
    }
}
