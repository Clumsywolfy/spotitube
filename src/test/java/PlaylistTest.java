import oose.dea.DAO.ILoginDAO;
import oose.dea.DAO.IPlaylistDAO;
import oose.dea.domain.Playlist;
import oose.dea.domain.User;
import oose.dea.resource.DTO.PlaylistDTO;
import oose.dea.resource.DTO.PlaylistsDTO;
import oose.dea.resource.PlaylistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlaylistTest {

    private ILoginDAO loginDAOMock;
    private IPlaylistDAO playlistDAOMock;
    private PlaylistService sut;
    private PlaylistsDTO playlistsDTO;
    private PlaylistDTO playlistDTO;
    private User user;

    private String token;
    private String username;
    private String password;
    private final int OK = 200;

    @BeforeEach
    public void setup() {
        token = "400";
        username = "Debbie";
        password = "Kauw";

        loginDAOMock = mock(ILoginDAO.class);
        playlistDAOMock = mock(IPlaylistDAO.class);
        sut = new PlaylistService();
        user = new User(username);

        playlistDTO = new PlaylistDTO();
        playlistDTO.id = 1;
        playlistDTO.name = "Metal";
        playlistDTO.owner = true;
        playlistDTO.tracks = new ArrayList<>();

        playlistsDTO = new PlaylistsDTO();
        playlistsDTO.playlists = new ArrayList<>();
        playlistsDTO.length = 100;
        playlistsDTO.playlists.add(playlistDTO);
    }

    @Test
    public void getAllPlaylistsTest() {
        ArrayList<Playlist> playlistsArray = new ArrayList<>();

        Playlist playlist = new Playlist(1);
        playlist.setName("luuk");
        playlist.setOwner("luuk");
        playlist.setTracks(new ArrayList<>());

        playlistsArray.add(playlist);

        try {
            when(loginDAOMock.selectUserFromToken(token)).thenReturn(user);
            when(playlistDAOMock.getAllPlaylists()).thenReturn(playlistsArray);
            sut.setLoginDAO(loginDAOMock);
            sut.setPlaylistDAO(playlistDAOMock);

            Response response = sut.getAllPlaylists(token);
            PlaylistsDTO playlistsDTOResponse = (PlaylistsDTO) response.getEntity();

            assertEquals(OK, response.getStatus());
            assertEquals(playlist.getId(), playlistsDTOResponse.playlists.get(0).id);

      } catch (Exception e) {
            fail(e);
        }
    }
}
