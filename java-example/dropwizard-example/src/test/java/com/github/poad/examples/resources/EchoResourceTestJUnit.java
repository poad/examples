package com.github.poad.examples.resources;

import com.github.poad.examples.impl.EchoImpl;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(DropwizardExtensionsSupport.class)
public class EchoResourceTestJUnit {
    public static final ResourceExtension RULE = ResourceExtension.builder()
            .addResource(new EchoImpl())
            .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .build();

    @Test
    public void test() {
        Client client = ClientBuilder.newClient();
        
        WebTarget target = client.target(RULE.getJerseyTest().target().getUri().toString());
        
        String response = target.queryParam("message", "hello").request().get(String.class);
        
        assertEquals(response, "hello");
    }

}
