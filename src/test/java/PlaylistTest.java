import oose.dea.DAO.ILoginDAO;
import oose.dea.DAO.IPlaylistDAO;
import oose.dea.domain.User;
import oose.dea.exceptions.badRequestException;
import oose.dea.exceptions.unauthorizedUserException;
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
    private PlaylistService playlistService;
    private PlaylistService playlistServiceMock;
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
        playlistService = new PlaylistService();
        playlistServiceMock = mock(PlaylistService.class);
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
        try {
            when(loginDAOMock.selectUserFromToken(token)).thenReturn(user);
            when(playlistServiceMock.getPlaylists(user)).thenReturn(playlistsDTO);

            Response response = playlistServiceMock.getAllPlaylists(token);
            PlaylistsDTO playlistsDTOResponse = (PlaylistsDTO) response.getEntity();

            assertEquals(OK, response.getStatus());

      } catch (Exception e) {
            fail(e);
        }
    }
}
