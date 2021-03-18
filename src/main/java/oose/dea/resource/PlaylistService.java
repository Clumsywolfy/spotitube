package oose.dea.resource;

import oose.dea.DAO.ILoginDAO;
import oose.dea.DAO.IPlaylistDAO;
import oose.dea.domain.Playlist;
import oose.dea.domain.Playlists;
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
    private IPlaylistDAO playlistDAO;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@QueryParam("token") String token) {
        User user = loginDAO.selectUserFromToken(token);

        if (token == null || user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        ArrayList playlists = playlistDAO.getAllPlaylists();
        PlaylistsDTO playlistsDTO = new PlaylistsDTO();
        playlistsDTO.playlists = new ArrayList<>();

        int length =  0;

        for(Object playlist : playlists){

            Playlist list = (Playlist) playlist;

            PlaylistDTO playlistDTO = new PlaylistDTO();

            playlistDTO.id = list.getId();
            playlistDTO.name = list.getName();
            if(list.getOwner().equals(user.getUsername())){
                playlistDTO.owner = true;
            } else {
                playlistDTO.owner = false;
            }
            playlistDTO.tracks = list.getTracks();
            length += list.getLength();

            playlistsDTO.playlists.add(playlistDTO);
        }

        playlistsDTO.length = length;

            return Response.status(Response.Status.OK).entity(playlistsDTO).build();
        }

    @Inject
    public void setLoginDAO(ILoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }

    @Inject
    public void setPlaylistDAO(IPlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }
}
