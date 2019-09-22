package com.github.poad.examples.java.webapps.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class RawExceptionMapper implements ExceptionMapper<Throwable> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Response toResponse(Throwable throwable) {
        try {
            if (throwable instanceof WebApplicationException) {
                Response originResponse = ((WebApplicationException) throwable).getResponse();

                return Response.status(originResponse.getStatus())
                        .type(MediaType.APPLICATION_JSON)
                        .entity(mapper.writeValueAsString(throwable.getMessage()))
                        .build();
            } else {
                return Response.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .type(MediaType.APPLICATION_JSON)
                        .entity(mapper.writeValueAsString(throwable.getMessage()))
                        .build();

            }
        } catch (JsonProcessingException e) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .type(MediaType.APPLICATION_JSON)
                    .entity(throwable.getMessage())
                    .build();
        }
    }
}
