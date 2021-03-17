package oose.dea.DAO;

import oose.dea.domain.User;

public interface ILoginDAO {
    User getLogin(String username, String password);
    void addTokenToDatabase(User user);
}
