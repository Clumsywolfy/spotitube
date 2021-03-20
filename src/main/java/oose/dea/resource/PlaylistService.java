package oose.dea.resource;

import oose.dea.DAO.ILoginDAO;
import oose.dea.DAO.IPlaylistDAO;
import oose.dea.domain.Playlist;
import oose.dea.domain.User;
import oose.dea.resource.DTO.PlaylistDTO;
import oose.dea.resource.DTO.PlaylistsDTO;
import oose.dea.resource.DTO.TrackDTO;
import oose.dea.resource.DTO.TracksDTO;

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

        if(token == null || id < 1){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if ( user == null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        playlistDAO.deletePlaylist(id, user.getUsername());
        PlaylistsDTO playlistsDTO = getPlaylists(user);

        return Response.status(Response.Status.OK).entity(playlistsDTO).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, PlaylistDTO playlistDTO){
        User user = loginDAO.selectUserFromToken(token);

        if(token == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (user == null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        playlistDAO.addPlaylist(playlistDTO.name, user.getUsername());
        PlaylistsDTO playlistsDTO = getPlaylists(user);

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

        if (user == null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        playlistDAO.editPlaylist(playlistDTO.name, id, user.getUsername());
        PlaylistsDTO playlistsDTO = getPlaylists(user);

        return Response.status(Response.Status.OK).entity(playlistsDTO).build();
    }

    @GET
    @Path("/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylistTracks(@QueryParam("token") String token, @PathParam("id") int id){
        User user = loginDAO.selectUserFromToken(token);

        if(token == null || id < 1){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (user == null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        TracksDTO tracksDTO = new TracksDTO();
        tracksDTO.tracks = playlistDAO.getAllPlaylistTracks(id);

        return Response.status(Response.Status.OK).entity(tracksDTO).build();
    }

    @DELETE
    @Path("/{id}/tracks/{trackId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteFromPlaylist(@PathParam("id") int id, @PathParam("trackId") int trackId, @QueryParam("token") String token){
        User user = loginDAO.selectUserFromToken(token);

        if(token == null || id < 1 || trackId < 1){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if ( user == null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        playlistDAO.deleteTrackFromPlaylist(id, trackId, user.getUsername());

        TracksDTO tracksDTO = new TracksDTO();
        tracksDTO.tracks = playlistDAO.getAllPlaylistTracks(id);

        return Response.status(Response.Status.OK).entity(tracksDTO).build();
    }

    @POST
    @Path("/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addToPlaylist(@PathParam("id") int id, @QueryParam("token") String token, TrackDTO trackDTO){
        User user = loginDAO.selectUserFromToken(token);

        if(token == null || id < 1 ){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if ( user == null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        playlistDAO.addTrackToPlaylist(id, trackDTO.id);
        playlistDAO.setTrackAvailable(trackDTO.offlineAvailable, trackDTO.id);

        TracksDTO tracksDTO = new TracksDTO();
        tracksDTO.tracks = playlistDAO.getAllPlaylistTracks(id);

        return Response.status(Response.Status.OK).entity(tracksDTO).build();
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
