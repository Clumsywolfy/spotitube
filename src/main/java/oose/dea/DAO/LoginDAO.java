package oose.dea.DAO;

import oose.dea.domain.User;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Default
public class LoginDAO  implements ILoginDAO{

    @Resource(name = "jdbc/starwars")
    DataSource dataSource;

    @Override
    public User getLogin(String username, String password) {
        String sql = "select * from users where username = ? AND password = ?";

        try(Connection connection = dataSource.getConnection()){

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,username);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                User user = new User(resultSet.getString("username"));
                user.setUser(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));

                return user;
            }

        } catch(SQLException exception){
            exception.printStackTrace();
        }

        return null;
    }

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }
}
