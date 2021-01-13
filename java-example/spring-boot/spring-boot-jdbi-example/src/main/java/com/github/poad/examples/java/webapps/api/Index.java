package com.github.poad.examples.java.webapps.api;

import com.github.poad.examples.java.webapps.interfaces.Message;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Path("/message")
public interface Index {
    @GET
    List<Message> message();

    @GET
    @Path("{id}")
    Message get(@NotNull @PathParam("id") String id);

    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    String create(@NotNull Message message);

    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @PUT
    boolean update(@PathParam("id") @NotNull String id, @NotNull Message message);

    @Path("{id}")
    @DELETE
    void delete(@NotNull @PathParam("id") String id);
}
