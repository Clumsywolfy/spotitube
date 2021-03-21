package oose.dea.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class connectionErrorExceptionMapper implements ExceptionMapper<connectionErrorException> {

    @Override
    public Response toResponse(connectionErrorException exception) {
        return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
    }
}

