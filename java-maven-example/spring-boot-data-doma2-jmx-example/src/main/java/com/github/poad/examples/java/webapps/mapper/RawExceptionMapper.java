package com.github.poad.examples.java.webapps.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RawExceptionMapper implements ExceptionMapper<Throwable> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Response toResponse(Throwable throwable) {
        try {
            if (throwable instanceof WebApplicationException) {
                Response originalResponce = ((WebApplicationException) throwable).getResponse();
                return Response.status(originalResponce.getStatus())
                        .type(MediaType.APPLICATION_JSON)
                        .entity(mapper.writeValueAsString(throwable.getMessage()))
                        .build();
            }
            return Response.status(500)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(mapper.writeValueAsString(throwable.getMessage()))
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(500)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}
