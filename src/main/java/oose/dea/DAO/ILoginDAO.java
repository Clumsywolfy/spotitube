package oose.dea.DAO;

import oose.dea.domain.User;
import oose.dea.exceptions.unauthorizedUserException;

public interface ILoginDAO {
    User getLogin(String username, String password) throws unauthorizedUserException;
    void addTokenToDatabase(User user);
    User selectUserFromToken(String token) throws unauthorizedUserException;
}
