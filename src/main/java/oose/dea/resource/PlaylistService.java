package oose.dea.resource;

import oose.dea.DAO.ILoginDAO;
import oose.dea.DAO.IPlaylistDAO;
import oose.dea.domain.Playlist;
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@QueryParam("token") String token) {
        User user = loginDAO.selectUserFromToken(token);
        PlaylistsDTO playlistsDTO = getPlaylists(user);

        if (token == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if ( user == null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

            return Response.status(Response.Status.OK).entity(playlistsDTO).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") int id, @QueryParam("token") String token){
        User user = loginDAO.selectUserFromToken(token);
        PlaylistsDTO playlistsDTO = getPlaylists(user);

        if(token == null || id < 1){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if ( user == null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        playlistDAO.deletePlaylist(id);
        return Response.status(Response.Status.OK).entity(playlistsDTO).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, PlaylistDTO playlistDTO){
        User user = loginDAO.selectUserFromToken(token);
        PlaylistsDTO playlistsDTO = getPlaylists(user);

        if(token == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (user == null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        playlistDAO.addPlaylist(playlistDTO.name, user.getUsername());
        return Response.status(Response.Status.OK).entity(playlistsDTO).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@PathParam("id") int id, @QueryParam("token") String token,PlaylistDTO playlistDTO){
        User user = loginDAO.selectUserFromToken(token);

        if(token == null || id < 1){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        playlistDAO.editPlaylist(playlistDTO.name, id, user.getUsername());
        PlaylistsDTO playlistsDTO = getPlaylists(user);

        return Response.status(Response.Status.OK).entity(playlistsDTO).build();
    }

    private PlaylistsDTO getPlaylists(User user){
        ArrayList<Playlist> playlists = playlistDAO.getAllPlaylists();
        PlaylistsDTO playlistsDTO = new PlaylistsDTO();
        playlistsDTO.playlists = new ArrayList<>();

        int length =  0;

        for(Object playlist : playlists){

            Playlist list = (Playlist) playlist;

            PlaylistDTO playlistDTO = new PlaylistDTO();

            playlistDTO.id = list.getId();
            playlistDTO.name = list.getName();
            playlistDTO.owner = list.getOwner().equals(user.getUsername());

            playlistDTO.tracks = list.getTracks();
            length += list.getLength();

            playlistsDTO.playlists.add(playlistDTO);
        }

        playlistsDTO.length = length;

        return playlistsDTO;
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
