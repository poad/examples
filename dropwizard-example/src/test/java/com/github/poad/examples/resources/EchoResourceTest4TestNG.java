package com.github.poad.examples.resources;

import io.dropwizard.Configuration;
import io.dropwizard.testing.junit.DropwizardAppRule;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.poad.examples.ExampleServer;

public class EchoResourceTest4TestNG {

    public static DropwizardAppRule<Configuration> RULE;

    @BeforeClass
    public static void setup() throws Exception {
        RULE = new DropwizardAppRule<>(ExampleServer.class, "example.yml");
        RULE.getTestSupport().before();
    }

    @Test
    public void test() {
        Client client = ClientBuilder.newClient();
        
        WebTarget target = client.target("http://localhost:" + RULE.getLocalPort() + "/");
        
        String response = target.queryParam("message", "hello").request().get(String.class);
        
        Assert.assertEquals(response, "hello");
    }
    
    @AfterClass
    public void tearDown() throws Exception {
        RULE.getTestSupport().after();
        if (RULE.getEnvironment().getApplicationContext().isStarted()) {
            RULE.getEnvironment().getApplicationContext().stop();
        }
    }
}
