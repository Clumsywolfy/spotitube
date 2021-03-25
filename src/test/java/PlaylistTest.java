import oose.dea.DAO.ILoginDAO;
import oose.dea.DAO.IPlaylistDAO;
import oose.dea.DAO.ITrackDAO;
import oose.dea.domain.Playlist;
import oose.dea.domain.Track;
import oose.dea.domain.User;
import oose.dea.rest.DTO.PlaylistDTO;
import oose.dea.rest.DTO.PlaylistsDTO;
import oose.dea.rest.DTO.TrackDTO;
import oose.dea.rest.PlaylistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlaylistTest {

    private ILoginDAO loginDAOMock;
    private IPlaylistDAO playlistDAOMock;
    private ITrackDAO trackDAOMock;
    private PlaylistService sut;
    private User user;
    private Playlist playlist;
    private Track track;
    private PlaylistDTO playlistDTO;
    private TrackDTO trackDTO;

    private String tokenToTest;
    private String usernameToTest;
    private final int OK = 200;
    private int idToTest;
    private String nameToTest;
    private String ownerToTest;
    private ArrayList tracksToTest;
    private ArrayList<Playlist> playlistsArray;
    private ArrayList<Track> tracksArray;

    @BeforeEach
    public void setup() {
        tokenToTest = "400";
        usernameToTest = "Debbie";
        idToTest = 1;
        nameToTest = "Metal";
        ownerToTest = "Debbie";
        tracksToTest = new ArrayList<>();
        playlistsArray = new ArrayList<>();
        tracksArray = new ArrayList<>();

        loginDAOMock = mock(ILoginDAO.class);
        playlistDAOMock = mock(IPlaylistDAO.class);
        trackDAOMock = mock(ITrackDAO.class);
        sut = new PlaylistService();
        user = new User(usernameToTest);
        playlistDTO = new PlaylistDTO();
        trackDTO = new TrackDTO();

        playlist = new Playlist(idToTest);
        playlist.setName(nameToTest);
        playlist.setOwner(ownerToTest);
        playlist.setTracks(tracksToTest);

        playlistsArray.add(playlist);

        track = new Track(idToTest);
        track.setTitle(nameToTest);
        track.setPerformer(ownerToTest);
        track.setDuration(400);
        track.setAlbum(nameToTest);
        track.setPlaycount(idToTest);
        track.setPublicationDate("2020");
        track.setDescription(nameToTest);
        track.setOfflineAvailable(false);

        tracksArray.add(track);

        sut.setLoginDAO(loginDAOMock);
        sut.setPlaylistDAO(playlistDAOMock);
        sut.setTrackDAO(trackDAOMock);
    }

    @Test
    public void getAllPlaylistsTest() {
        try {
            when(loginDAOMock.selectUserFromToken(tokenToTest)).thenReturn(user);
            when(playlistDAOMock.getAllPlaylists()).thenReturn(playlistsArray);

            Response response = sut.getAllPlaylists(tokenToTest);
            PlaylistsDTO playlistsDTOResponse = (PlaylistsDTO) response.getEntity();

            assertEquals(OK, response.getStatus());
            assertEquals(playlist.getId(), playlistsDTOResponse.playlists.get(0).id);

      } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void deletePlaylistTest() {
        try {
            when(loginDAOMock.selectUserFromToken(tokenToTest)).thenReturn(user);
            when(playlistDAOMock.getAllPlaylists()).thenReturn(playlistsArray);

            Response response = sut.deletePlaylist(idToTest, tokenToTest);
            PlaylistsDTO playlistsDTOResponse = (PlaylistsDTO) response.getEntity();

            assertEquals(OK, response.getStatus());
            assertEquals(playlist.getId(), playlistsDTOResponse.playlists.get(0).id);

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void addPlaylistTest() {
        try {
            when(loginDAOMock.selectUserFromToken(tokenToTest)).thenReturn(user);
            when(playlistDAOMock.getAllPlaylists()).thenReturn(playlistsArray);

            Response response = sut.addPlaylist(tokenToTest, playlistDTO);
            PlaylistsDTO playlistsDTOResponse = (PlaylistsDTO) response.getEntity();

            assertEquals(OK, response.getStatus());
            assertEquals(playlist.getId(), playlistsDTOResponse.playlists.get(0).id);

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void editPlaylistTest() {
        try {
            when(loginDAOMock.selectUserFromToken(tokenToTest)).thenReturn(user);
            when(playlistDAOMock.getAllPlaylists()).thenReturn(playlistsArray);

            Response response = sut.editPlaylist(idToTest, tokenToTest, playlistDTO);
            PlaylistsDTO playlistsDTOResponse = (PlaylistsDTO) response.getEntity();

            assertEquals(OK, response.getStatus());
            assertEquals(playlist.getId(), playlistsDTOResponse.playlists.get(0).id);

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void getAllPlaylistTracksTest() {
        try {
            when(trackDAOMock.getAllTracks(idToTest,false)).thenReturn(tracksArray);

            Response response = sut.getAllPlaylistTracks(tokenToTest, idToTest);

            assertEquals(OK, response.getStatus());

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void deleteFromPlaylistTest() {
        try {
            when(loginDAOMock.selectUserFromToken(tokenToTest)).thenReturn(user);
            when(trackDAOMock.getAllTracks(idToTest,false)).thenReturn(tracksArray);

            Response response = sut.deleteFromPlaylist(idToTest, idToTest, tokenToTest);

            assertEquals(OK, response.getStatus());

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void addToPlaylistTest() {
        try{
            when(trackDAOMock.getAllTracks(idToTest,false)).thenReturn(tracksArray);

            Response response = sut.addToPlaylist(idToTest, tokenToTest, trackDTO);

            assertEquals(OK, response.getStatus());

        } catch (Exception e) {
            fail(e);
        }
    }
}
