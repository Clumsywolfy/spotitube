package oose.dea.service;

import oose.dea.DAO.ILoginDAO;
import oose.dea.DAO.LoginDAO;
import oose.dea.DTO.LoginDTO;
import oose.dea.domain.Login;

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
    public Response getLogin(LoginDTO loginDTO){

        Login login = loginDAO.getLogin();

        if(login.getUsername().equals("Debbie") && login.getPassword().equals("Kauw")){
            loginDTO = new LoginDTO();
            loginDTO.token = login.getToken();
            loginDTO.user = login.getUser();

            return Response.status(200).entity(loginDTO).build();

        } else {

            return Response.status(404).build();
        }
    }

    @Inject
    public void setLoginDAO(ILoginDAO loginDAO){
        this.loginDAO = loginDAO;
    }
}
