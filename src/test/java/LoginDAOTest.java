import oose.dea.DAO.LoginDAO;
import oose.dea.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class LoginDAOTest {

    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private LoginDAO loginDAO;

    @BeforeEach
    public void setup(){
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        loginDAO = new LoginDAO();
        loginDAO.setDataSource(dataSource);
    }

    @Test
    public void getLoginTest(){
        try {
            String expectedSQL = "select * from users where username = ? AND password = ?";
            String usernameToTest = "Debbie";
            String passwordToTest = "Kauw";

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            User user = loginDAO.getLogin(usernameToTest,passwordToTest);

            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1,usernameToTest);
            verify(preparedStatement).setString(2, passwordToTest);
            verify(preparedStatement).executeQuery();

            assertNull(user);

        }
        catch (Exception e){
            fail();
            e.getMessage();
        }
    }

    @Test
    public void getLoginResultsTest(){
        try {
            String expectedSQL = "select * from users where username = ? AND password = ?";
            String usernameToTest = "Debbie";
            String passwordToTest = "Kauw";
            String userToTest = "Debbie Kauw";

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);
            when(resultSet.getString("username")).thenReturn(usernameToTest);
            when(resultSet.getString("password")).thenReturn(passwordToTest);
            when(resultSet.getString("user")).thenReturn(userToTest);

            User user = loginDAO.getLogin(usernameToTest,passwordToTest);

            assertEquals(usernameToTest, user.getUsername());
            assertEquals(passwordToTest, user.getPassword());
            assertEquals(userToTest, user.getUser());
        }
        catch (Exception e){
            fail();
            e.getMessage();
        }
    }

    @Test
    public void addTokenToDatabaseTest(){
        try {
            String expectedSQL = "Update users Set token = ? Where username = ?";
            String usernameToTest = "Debbie";

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            User user = new User(usernameToTest);
            loginDAO.addTokenToDatabase(user);

            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, user.getToken());
            verify(preparedStatement).setString(2, user.getUsername());
            verify(preparedStatement).executeUpdate();
        }
        catch (Exception e){
            fail();
            e.getMessage();
        }
    }
}
