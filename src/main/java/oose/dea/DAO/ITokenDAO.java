package oose.dea.DAO;

import oose.dea.domain.Token;

public interface ITokenDAO {
    Token generateToken(String user);
}
