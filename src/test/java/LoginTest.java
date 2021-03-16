import oose.dea.DTO.LoginDTO;
import oose.dea.service.LoginService;
import oose.dea.domain.Login;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {

    private LoginService loginController;
    private LoginDTO loginDTO;
    private Login login;

    @BeforeEach
    public void setup(){
        loginController = new LoginService();
        loginDTO = new LoginDTO();
        login = new Login();
    }

    @Test
    public void loginSucces(){
        String token = "Welkom";
        String user = "Debbie Kauw";

        loginDTO.username = "Debbie";
        loginDTO.password = "Kauw";

        Response response = loginController.getLogin(loginDTO);
        loginDTO = (LoginDTO) response.getEntity();

        assertEquals(200, response.getStatus());
        assertEquals(token, loginDTO.token);
        assertEquals(user, loginDTO.user);
    }

    @Test
    public void loginFailed(){
        loginDTO.username = "Marien";
        loginDTO.password = "Laika";

        Response response = loginController.getLogin(loginDTO);

        assertEquals(404, response.getStatus());
    }

}
