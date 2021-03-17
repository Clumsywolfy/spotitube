package oose.dea.service;

import oose.dea.DAO.ILoginDAO;
import oose.dea.DTO.TokenDTO;
import oose.dea.DTO.UserDTO;
import oose.dea.domain.Token;
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

        if(user == null){
            return Response.status(401).entity("User doesn't exsist or the password is wrong.").build();

        }
            TokenDTO tokenDTO = new TokenDTO();
            Token token = new Token(userDTO.username);
            tokenDTO.token = token.getToken();
            tokenDTO.user = token.getUser();

            return Response.status(200).entity(tokenDTO).build();
        }

    @Inject
    public void setLoginDAO(ILoginDAO loginDAO){
        this.loginDAO = loginDAO;
    }
}
