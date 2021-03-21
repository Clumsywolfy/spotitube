import oose.dea.DAO.ILoginDAO;
import oose.dea.DAO.IPlaylistDAO;
import oose.dea.domain.User;
import oose.dea.resource.DTO.PlaylistsDTO;
import oose.dea.resource.DTO.TokenDTO;
import oose.dea.resource.DTO.UserDTO;
import oose.dea.resource.PlaylistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlaylistTest {

    private ILoginDAO loginDAOMock;
    private IPlaylistDAO playlistDAOMock;
    private PlaylistService playlistService;
    private PlaylistsDTO playlistsDTO;
    private User user;

    private String token;
    private String username;

    @BeforeEach
    public void setup(){
        token = "400";
        username = "Debbie";

        loginDAOMock = mock(ILoginDAO.class);
        playlistDAOMock = mock(IPlaylistDAO.class);
        playlistService = new PlaylistService();
        playlistsDTO = new PlaylistsDTO();
        user = new User(username);
    }

   /* @Test
    public void getAllPlaylistsTest(){

        when(loginDAOMock.selectUserFromToken(token)).thenReturn(user);
        when(playlistService.getPlaylists(user)).thenReturn();

        Response response =


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
    }*/
}
