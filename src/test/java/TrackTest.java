import oose.dea.DAO.ILoginDAO;
import oose.dea.DAO.ITrackDAO;
import oose.dea.domain.Track;
import oose.dea.domain.User;
import oose.dea.resource.DTO.*;
import oose.dea.resource.TrackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrackTest {

    private ITrackDAO trackDAOMock;
    private ILoginDAO loginDAOMock;
    private TrackService sut;
    private Track track;
    private ArrayList<Track> tracksArray;

    private final int OK = 200;
    private int idToTest;
    private int playlistToTest;
    private String tokenToTest;
    private String usernameToTest;

    @BeforeEach
    public void setup(){
        trackDAOMock = mock(ITrackDAO.class);
        loginDAOMock = mock((ILoginDAO.class));
        sut = new TrackService();

        idToTest = 1;
        playlistToTest = 1;
        tokenToTest = "400";
        tracksArray = new ArrayList<>();
        usernameToTest = "Debbie";

        track = new Track(idToTest);
        track.setTitle(usernameToTest);
        track.setPerformer(usernameToTest);
        track.setDuration(playlistToTest);
        track.setAlbum(usernameToTest);
        track.setPlaycount(playlistToTest);
        track.setPublicationDate(usernameToTest);
        track.setDescription(usernameToTest);
        track.setOfflineAvailable(false);

        tracksArray.add(track);

        sut.setTrackDAO(trackDAOMock);
        sut.setLoginDAO(loginDAOMock);
    }

    @Test
    public void getAllAvailableTracksTest() {
        try {
            when(trackDAOMock.getAllTracks(playlistToTest,true)).thenReturn(tracksArray);

            Response response = sut.getAllAvailableTracks(playlistToTest, tokenToTest);

            assertEquals(OK, response.getStatus());

        } catch (Exception e) {
            fail(e);
        }
    }
}
