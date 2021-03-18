package oose.dea.resource;

import oose.dea.DAO.ILoginDAO;
import oose.dea.domain.User;
import oose.dea.resource.DTO.PlaylistDTO;
import oose.dea.resource.DTO.PlaylistsDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("playlists")
public class PlaylistService {

    private ILoginDAO loginDAO;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@QueryParam("token") String token) {
        User user = loginDAO.selectUserFromToken(token);

        if (token == null || user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

            return Response.status(Response.Status.OK).entity(playlistsDTO).build();
        }

    @Inject
    public void setLoginDAO(ILoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }
}
