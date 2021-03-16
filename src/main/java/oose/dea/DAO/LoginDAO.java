package oose.dea.DAO;

import oose.dea.domain.Login;

import javax.enterprise.inject.Default;

@Default
public class LoginDAO  implements ILoginDAO{

    @Override
    public Login getLogin() {
        Login login = new Login();

        login.setUser("Debbie Kauw");
        login.setPassword("Kauw");
        login.setUsername("Debbie");
        login.setToken("Welkom");

        return login;
    }
}
