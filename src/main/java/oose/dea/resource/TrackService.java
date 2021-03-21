package oose.dea.resource;

import oose.dea.DAO.ILoginDAO;
import oose.dea.DAO.ITrackDAO;
import oose.dea.domain.Track;
import oose.dea.exceptions.badRequestException;
import oose.dea.exceptions.unauthorizedUserException;
import oose.dea.resource.DTO.TrackDTO;
import oose.dea.resource.DTO.TracksDTO;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("tracks")
public class TrackService {

    private ILoginDAO loginDAO;
    private ITrackDAO trackDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAvailableTracks(@QueryParam("forPlaylist") int playlist, @QueryParam("token") String token) throws unauthorizedUserException, badRequestException {
        loginDAO.selectUserFromToken(token);
        TracksDTO tracksDTO = getTracks(playlist);

        if (token == null || playlist < 1) {
            throw new badRequestException("Token of playlist id is onjuist.");
        }

        return Response.status(Response.Status.OK).entity(tracksDTO).build();
    }

    public TracksDTO getTracks(int playlist){
        ArrayList<Track> tracks  = trackDAO.getAllTracks(playlist, true);
        TracksDTO tracksDTO = new TracksDTO();
        tracksDTO.tracks = new ArrayList<>();

        for(Object track : tracks){

            Track t1 = (Track) track;

            TrackDTO trackDTO = new TrackDTO();
            trackDTO.id = t1.getId();
            trackDTO.title = t1.getTitle();
            trackDTO.performer = t1.getPerformer();
            trackDTO.duration = t1.getDuration();
            trackDTO.album = ((t1.getAlbum() != null) ? t1.getAlbum() : "undefined");
            trackDTO.playcount = t1.getPlaycount();
            trackDTO.publicationDate = ((t1.getPublicationDate() != null) ? t1.getPublicationDate() : "undefined");
            trackDTO.description = ((t1.getDescription() != null) ? t1.getDescription() : "undefined");
            trackDTO.offlineAvailable = t1.isOfflineAvailable();

            tracksDTO.tracks.add(trackDTO);
        }
        return tracksDTO;
    }

    @Inject
    public void setLoginDAO(ILoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }


    @Inject
    public void setTrackDAO(ITrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }
}

