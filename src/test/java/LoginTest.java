
import oose.dea.DAO.ILoginDAO;
import oose.dea.exceptions.unauthorizedUserException;
import oose.dea.resource.DTO.UserDTO;
import oose.dea.resource.LoginService;
import oose.dea.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginTest {

    private LoginService loginServiceMock;
    private UserDTO userDTO;
    private User user;
    private ILoginDAO loginDAOMock;


    @BeforeEach
    public void setup(){
        String username = "Debbie";
        String password = "Kauw";

        loginServiceMock = new LoginService();
        userDTO = new UserDTO();
        user = new User(username);
        loginDAOMock = mock(ILoginDAO.class);

        userDTO.user = username;
        userDTO.password = password;
    }

    @Test
    public void loginSuccesTest() {
        try{
        when(loginDAOMock.getLogin(userDTO.user, userDTO.password)).thenReturn(user);
        loginServiceMock.setLoginDAO(loginDAOMock);

        Response response = loginServiceMock.getLogin(userDTO);

        assertEquals(200, response.getStatus());
        } catch (Exception e) {
            fail(e);
        }
    }
}
