package oose.dea.exceptions.mappers;

import oose.dea.exceptions.unauthorizedUserException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class unauthorizedUserExceptionMapper implements ExceptionMapper<unauthorizedUserException> {

    @Override
    public Response toResponse(unauthorizedUserException exception) {
        return Response.status(Response.Status.UNAUTHORIZED).entity(exception.getMessage()).build();
    }
}
