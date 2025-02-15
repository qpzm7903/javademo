package com.example;

import org.apache.http.client.methods.HttpGet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class HttpClientWithRetryTest {
    private HttpClientWithRetry client;

    @Before
    public void setUp() {
        client = new HttpClientWithRetry();
    }

    @After
    public void tearDown() throws Exception {
        client.close();
    }

    @Test
    public void testHttpGetRequest() throws Exception {
        // 使用一个可靠的测试API
        HttpGet request = new HttpGet("https://httpbin.org/get");
        String response = client.execute(request);
        assertNotNull(response);
    }

    @Test(expected = RuntimeException.class)
    public void testHttpGetRequestWithRetry() throws Exception {
        // 使用一个不存在的地址来测试重试机制
        try {
            HttpGet request = new HttpGet("https://httpbin.org/status/500");
            String execute = client.execute(request);
            System.out.println(execute);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
} 