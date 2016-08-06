package org.bitbucket.poad1010.resources;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by ken-yo on 2016/08/06.
 */
@Path("/hello")
public class HelloResource implements Resource {

    @GET
    public String message() {
        return "Hello";
    }

}