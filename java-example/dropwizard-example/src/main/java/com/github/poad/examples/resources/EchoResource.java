package com.github.poad.examples.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/")
@Produces("text/plain")
public interface EchoResource {

    @GET
    public String echo(@QueryParam("message") String message);
}
