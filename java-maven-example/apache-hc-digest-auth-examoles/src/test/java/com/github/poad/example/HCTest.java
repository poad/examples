package com.github.poad.example;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HCTest {
    @Test
    public void test() throws IOException {
        HttpHost target = new HttpHost("httpbin.org", 80, "http");
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(target.getHostName(), target.getPort()),
                new UsernamePasswordCredentials("user", "passwd"));
        try (CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build()) {

            // Create AuthCache instance
            AuthCache authCache = new BasicAuthCache();
            // Generate DIGEST scheme object, initialize it and add it to the local
            // auth cache
            DigestScheme digestAuth = new DigestScheme();
            // Suppose we already know the realm name
            digestAuth.overrideParamter("realm", "some realm");
            // Suppose we already know the expected nonce value
            digestAuth.overrideParamter("nonce", "whatever");
            authCache.put(target, digestAuth);

            // Add AuthCache to the execution context
            HttpClientContext localContext = HttpClientContext.create();
            localContext.setAuthCache(authCache);

            HttpGet httpget = new HttpGet("http://httpbin.org/digest-auth/auth/user/passwd");

            System.out.println("Executing request " + httpget.getRequestLine() + " to target " + target);
            int status = 999;
            for (int i = 0; i < 3; i++) {
                try (CloseableHttpResponse response = httpclient.execute(target, httpget, localContext)) {
                    System.out.println("----------------------------------------");
                    System.out.println(response.getStatusLine());
                    System.out.println(EntityUtils.toString(response.getEntity()));
                    status = response.getStatusLine().getStatusCode();
                }
            }
            assertEquals(status, 200);
        }

    }
}
