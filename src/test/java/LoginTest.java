
import oose.dea.DAO.ILoginDAO;
import oose.dea.DTO.UserDTO;
import oose.dea.service.LoginService;
import oose.dea.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginTest {

    private LoginService loginService;
    private UserDTO userDTO;
    private User login;
    private ILoginDAO loginDAOMock;


    @BeforeEach
    public void setup(){
        String username = "Debbie";
        String password = "Kauw";

        loginService = new LoginService();
        userDTO = new UserDTO();
        login = new User(username);
        loginDAOMock = mock(ILoginDAO.class);

        userDTO.username = username;
        userDTO.password = password;
    }

    @Test
    public void loginSucces(){
        when(loginDAOMock.getLogin(userDTO.username, userDTO.password)).thenReturn(login);
        loginService.setLoginDAO(loginDAOMock);

        Response response = loginService.getLogin(userDTO);

        assertEquals(200, response.getStatus());

    }

   @Test
    public void loginFailed(){
       when(loginDAOMock.getLogin(userDTO.username, userDTO.password)).thenReturn(null);
       loginService.setLoginDAO(loginDAOMock);

       Response response = loginService.getLogin(userDTO);

       assertEquals(401, response.getStatus());
    }

}
