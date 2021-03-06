package oose.dea.rest;

import oose.dea.DAO.ILoginDAO;
import oose.dea.exceptions.unauthorizedUserException;
import oose.dea.rest.DTO.TokenDTO;
import oose.dea.rest.DTO.UserDTO;
import oose.dea.domain.User;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("login")
public class LoginService {

    private ILoginDAO loginDAO;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getLogin(UserDTO userDTO) throws unauthorizedUserException {

        User user = loginDAO.getLogin(userDTO.user, userDTO.password);

            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.token = user.getToken();
            tokenDTO.user = user.getUser();
            loginDAO.addTokenToDatabase(user);

            return Response.status(Response.Status.OK).entity(tokenDTO).build();
        }

    @Inject
    public void setLoginDAO(ILoginDAO loginDAO){
        this.loginDAO = loginDAO;
    }
}
