package org.bitbucket.poad1010.example.springboot.jpa.resource;

import org.bitbucket.poad1010.example.springboot.jpa.request.CreateRequest;
import org.bitbucket.poad1010.example.springboot.jpa.request.UpdateRequest;
import org.bitbucket.poad1010.example.springboot.jpa.response.MessageResponse;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by ken-yo on 2016/08/06.
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("/message")
public interface MessageResource {
    @GET
    List<MessageResponse> list();

    @GET
    @Path("{id}")
    MessageResponse get(@NotNull @PathParam("id") String id);

    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    MessageResponse create(CreateRequest request);

    @Consumes(MediaType.APPLICATION_JSON)
    @PUT
    MessageResponse update(UpdateRequest request);

    @Path("{id}")
    @DELETE
    void delete(@NotNull @PathParam("id") String id);
}
