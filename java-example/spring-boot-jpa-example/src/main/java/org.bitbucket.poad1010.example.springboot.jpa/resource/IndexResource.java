package org.bitbucket.poad1010.example.springboot.jpa.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by ken-yo on 2016/08/06.
 */
@Path("/")
public class IndexResource {
    @GET
    public String get() {
        return "Hello World!";
    }
}
