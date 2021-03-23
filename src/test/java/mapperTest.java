import oose.dea.exceptions.badRequestException;
import oose.dea.exceptions.badRequestExceptionMapper;
import oose.dea.exceptions.unauthorizedUserException;
import oose.dea.exceptions.unauthorizedUserExceptionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import static org.junit.jupiter.api.Assertions.*;

public class mapperTest {

    private badRequestExceptionMapper badRequestSut;
    private badRequestException badRequest;

    private unauthorizedUserExceptionMapper unauthorizedUserSut;
    private unauthorizedUserException unauthorizedUser;

    private String message;

    @BeforeEach
    public void setup(){
        message = "Something went wrong";

        badRequestSut = new badRequestExceptionMapper();
        badRequest = new badRequestException(message);

        unauthorizedUserSut = new unauthorizedUserExceptionMapper();
        unauthorizedUser = new unauthorizedUserException(message);
    }

    @Test
    public void badRequestMapperTest(){

        Response response = badRequestSut.toResponse(badRequest);

        assertEquals(400, response.getStatus());
    }

    @Test
    public void unauthorizedUserMapperTest(){

        Response response = unauthorizedUserSut.toResponse(unauthorizedUser);

        assertEquals(401, response.getStatus());

    }
}

