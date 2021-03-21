package oose.dea.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class badRequestExceptionMapper implements ExceptionMapper<badRequestException> {

    @Override
    public Response toResponse(badRequestException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
    }
}


