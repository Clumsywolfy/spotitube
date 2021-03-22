
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

    private LoginService loginService;
    private UserDTO userDTO;
    private User user;
    private ILoginDAO loginDAOMock;


    @BeforeEach
    public void setup(){
        String username = "Debbie";
        String password = "Kauw";
        String token = "400";

        loginService = new LoginService();
        userDTO = new UserDTO();
        user = new User(username);
        loginDAOMock = mock(ILoginDAO.class);

        userDTO.username = username;
        userDTO.password = password;
        user.setToken(token);
    }

    @Test
    public void loginSuccesTest() throws unauthorizedUserException {
        when(loginDAOMock.getLogin(userDTO.username, userDTO.password)).thenReturn(user);
        loginService.setLoginDAO(loginDAOMock);

        Response response = loginService.getLogin(userDTO);

        assertEquals(200, response.getStatus());
    }
}
