package org.bitbucket.poad.examples.jersey.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by ken-yo on 2016/09/24.
 */
@Path("hello")
public class HelloResource implements Resource {
    public static final String CLICHED_MESSAGE = "Hello";
    @Produces(MediaType.TEXT_PLAIN)
    @GET
    public String get() {
        logger.info("called!");
        return CLICHED_MESSAGE;
    }
}
