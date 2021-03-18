package oose.dea.resource;

import oose.dea.DAO.ILoginDAO;
import oose.dea.resource.DTO.TokenDTO;
import oose.dea.resource.DTO.UserDTO;
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
    public Response getLogin(UserDTO userDTO){

        User user = loginDAO.getLogin(userDTO.username, userDTO.password);

        if(user == null || user.getToken() == null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.token = user.getToken();
            tokenDTO.user = user.getUser();
            loginDAO.addTokenToDatabase(user);

            return Response.status(Response.Status.CREATED).entity(tokenDTO).build();
        }

    @Inject
    public void setLoginDAO(ILoginDAO loginDAO){
        this.loginDAO = loginDAO;
    }

}
