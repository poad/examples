package com.github.poad.examples.resources;

import io.dropwizard.Configuration;
import io.dropwizard.testing.junit.DropwizardAppRule;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.testng.Assert;

import com.github.poad.examples.ExampleServer;

public class EchoResourceTest4JUnit {

    @ClassRule
    public static DropwizardAppRule<Configuration> RULE =
            new DropwizardAppRule<>(ExampleServer.class, "example.yml");
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        Client client = ClientBuilder.newClient();
        
        WebTarget target = client.target("http://localhost:" + RULE.getLocalPort() + "/");
        
        String response = target.queryParam("message", "hello").request().get(String.class);
        
        Assert.assertEquals(response, "hello");
    }

}
