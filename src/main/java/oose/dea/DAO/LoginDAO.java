package oose.dea.DAO;

import oose.dea.domain.User;
import oose.dea.exceptions.badRequestException;
import oose.dea.exceptions.unauthorizedUserException;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Logger;

@Default
public class LoginDAO implements ILoginDAO{

    private Logger logger = Logger.getLogger(getClass().getName());

    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    @Override
    public User getLogin(String username, String password) throws unauthorizedUserException {
        String loginQuery = "SELECT * FROM users WHERE username = ? AND password = ?";
        String token = UUID.randomUUID().toString();

        try(Connection connection = dataSource.getConnection()){

            PreparedStatement statement = connection.prepareStatement(loginQuery);
            statement.setString(1,username);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                User user = new User(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setUser(resultSet.getString("user"));
                user.setToken(token);
                return user;
            }
        } catch(SQLException exception){
            logger.severe(exception.getMessage());
        }
        throw new unauthorizedUserException("Gebruiker bestaat niet.");
    }

    @Override
    public void addTokenToDatabase(User user){
        String addTokenQuery = "UPDATE users SET token = ? WHERE username = ?";

        try(Connection connection = dataSource.getConnection()){

            PreparedStatement statement = connection.prepareStatement(addTokenQuery);
            statement.setString(1, user.getToken());
            statement.setString(2, user.getUsername());;
            statement.executeUpdate();

        } catch(SQLException exception){
            logger.severe(exception.getMessage());
        }
    }

    @Override
    public User selectUserFromToken(String token) throws badRequestException {
        String userQuery = "SELECT * FROM users WHERE token = ?";

        try(Connection connection = dataSource.getConnection()){

            PreparedStatement statement = connection.prepareStatement(userQuery);
            statement.setString(1,token);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                User user = new User(resultSet.getString("username"));;
                return user;
            }

        } catch(SQLException exception){
            logger.severe(exception.getMessage());
        }
        throw new badRequestException("Token is onjuist.");
    }

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }
}
