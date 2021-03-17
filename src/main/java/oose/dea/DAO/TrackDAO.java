package oose.dea.DAO;

import oose.dea.domain.Track;
import oose.dea.domain.User;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
/*
@Default
public class TrackDAO implements ITrackDAO{

    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    @Override
    public User getLogin(String username, String password) {
        String loginQuery = "select * from users where username = ? AND password = ?";
        String token = UUID.randomUUID().toString();

        try(Connection connection = dataSource.getConnection()){

            PreparedStatement statement = connection.prepareStatement(loginQuery);
            statement.setString(1,username);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                User user = new User(resultSet.getString("username"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setUser(resultSet.getString("user"));
                user.setToken(token);
                return user;
            }
        } catch(SQLException exception){
            exception.printStackTrace();
        }
        return null;
    }
    @Override
    public ArrayList<Track> getTracks() {
        return null;
    }
}
*/