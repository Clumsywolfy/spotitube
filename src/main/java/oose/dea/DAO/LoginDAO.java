package oose.dea.DAO;

import oose.dea.domain.User;
import oose.dea.exceptions.unauthorizedUserException;
import oose.dea.exceptions.connectionErrorException;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Default
public class LoginDAO  implements ILoginDAO{

    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    @Override
    public User getLogin(String username, String password) throws unauthorizedUserException {
        String loginQuery = "select * from users where username = ? AND password = ?";
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
            throw new connectionErrorException(exception.toString());
        }
        throw new unauthorizedUserException("Gebruiker bestaat niet.");
    }

    @Override
    public void addTokenToDatabase(User user){
        String addTokenQuery = "Update users Set token = ? Where username = ?";

        try(Connection connection = dataSource.getConnection()){

            PreparedStatement statement = connection.prepareStatement(addTokenQuery);
            statement.setString(1, user.getToken());
            statement.setString(2, user.getUsername());;
            statement.executeUpdate();

        } catch(SQLException exception){
            throw new connectionErrorException(exception.toString());
        }
    }

    @Override
    public User selectUserFromToken(String token) throws unauthorizedUserException {
        String userQuery = "select * from users where token = ?";

        try(Connection connection = dataSource.getConnection()){

            PreparedStatement statement = connection.prepareStatement(userQuery);
            statement.setString(1,token);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                User user = new User(resultSet.getString("username"));;
                return user;
            }

        } catch(SQLException exception){
            throw new connectionErrorException(exception.toString());
        }
        throw new unauthorizedUserException("Gebruiker bestaat niet.");
    }

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }
}
