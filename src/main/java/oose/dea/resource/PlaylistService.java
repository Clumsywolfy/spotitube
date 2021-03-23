package oose.dea.resource;

import oose.dea.DAO.ILoginDAO;
import oose.dea.DAO.IPlaylistDAO;
import oose.dea.DAO.ITrackDAO;
import oose.dea.domain.Playlist;
import oose.dea.domain.User;
import oose.dea.exceptions.unauthorizedUserException;
import oose.dea.exceptions.badRequestException;
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
    private ITrackDAO trackDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@QueryParam("token") String token) throws badRequestException {
        User user = loginDAO.selectUserFromToken(token);
        return Response.status(Response.Status.OK).entity(getPlaylists(user)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") int id, @QueryParam("token") String token) throws  badRequestException{
        User user = loginDAO.selectUserFromToken(token);
        playlistDAO.deletePlaylist(id, user.getUsername());
        return Response.status(Response.Status.OK).entity(getPlaylists(user)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, PlaylistDTO playlistDTO) throws badRequestException {
        User user = loginDAO.selectUserFromToken(token);
        playlistDAO.addPlaylist(playlistDTO.name, user.getUsername());
        return Response.status(Response.Status.OK).entity(getPlaylists(user)).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@PathParam("id") int id, @QueryParam("token") String token,PlaylistDTO playlistDTO) throws badRequestException {
        User user = loginDAO.selectUserFromToken(token);
        playlistDAO.editPlaylist(playlistDTO.name, id, user.getUsername());
        return Response.status(Response.Status.OK).entity(getPlaylists(user)).build();
    }

    @GET
    @Path("/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylistTracks(@QueryParam("token") String token, @PathParam("id") int id) throws  badRequestException {
        loginDAO.selectUserFromToken(token);
        TracksDTO tracksDTO = new TracksDTO();
        tracksDTO.tracks = trackDAO.getAllTracks(id,false);
        return Response.status(Response.Status.OK).entity(tracksDTO).build();
    }

    @DELETE
    @Path("/{id}/tracks/{trackId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteFromPlaylist(@PathParam("id") int id, @PathParam("trackId") int trackId, @QueryParam("token") String token) throws badRequestException {
        User user = loginDAO.selectUserFromToken(token);
        playlistDAO.deleteTrackFromPlaylist(id, trackId, user.getUsername());
        TracksDTO tracksDTO = new TracksDTO();
        tracksDTO.tracks = trackDAO.getAllTracks(id,false);
        return Response.status(Response.Status.OK).entity(tracksDTO).build();
    }

    @POST
    @Path("/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addToPlaylist(@PathParam("id") int id, @QueryParam("token") String token, TrackDTO trackDTO) throws badRequestException {
        loginDAO.selectUserFromToken(token);
        playlistDAO.addTrackToPlaylist(id, trackDTO.id);
        trackDAO.setTrackAvailable(trackDTO.offlineAvailable, trackDTO.id);
        TracksDTO tracksDTO = new TracksDTO();
        tracksDTO.tracks = trackDAO.getAllTracks(id,false);
        return Response.status(Response.Status.OK).entity(tracksDTO).build();
    }

    public PlaylistsDTO getPlaylists(User user){
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

    @Inject
    public void setTrackDAO(ITrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }
}
