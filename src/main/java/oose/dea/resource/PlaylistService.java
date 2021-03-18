package oose.dea.resource;

import oose.dea.resource.DTO.PlaylistDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("playlists")
public class PlaylistService {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@QueryParam("token") String token) {
        if (token.equals("1234")) {
            PlaylistDTO playlistDTO = new PlaylistDTO();
            playlistDTO.id = 1;
            playlistDTO.name = "metal";
            playlistDTO.owner = true;

            return Response.status(Response.Status.OK).entity(playlistDTO).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

}
