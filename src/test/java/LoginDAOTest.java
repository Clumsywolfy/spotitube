import oose.dea.DAO.LoginDAO;
import oose.dea.domain.User;
import oose.dea.exceptions.badRequestException;
import oose.dea.exceptions.unauthorizedUserException;
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
    private LoginDAO sut;

    String usernameToTest;
    String passwordToTest;

    @BeforeEach
    public void setup(){
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        sut = new LoginDAO();
        sut.setDataSource(dataSource);

        usernameToTest = "Debbie";
        passwordToTest = "Kauw";
    }

    @Test
    public void getLoginTest(){
        try {
            String expectedSQL = "SELECT * FROM users WHERE username = ? AND password = ?";
            String expectedError = "Gebruiker bestaat niet.";

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            Exception exception = assertThrows(unauthorizedUserException.class, () -> { sut.getLogin(usernameToTest,passwordToTest);});

            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1,usernameToTest);
            verify(preparedStatement).setString(2, passwordToTest);
            verify(preparedStatement).executeQuery();

            String actualError = exception.getMessage();

            assertTrue(actualError.contains(expectedError));
        }
        catch (Exception e){
            fail();
            e.getMessage();
        }
    }

    @Test
    public void getLoginResultsTest(){
        try {
            String expectedSQL = "SELECT * FROM users WHERE username = ? AND password = ?";
            String userToTest = "Debbie Kauw";

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);
            when(resultSet.getString("username")).thenReturn(usernameToTest);
            when(resultSet.getString("password")).thenReturn(passwordToTest);
            when(resultSet.getString("user")).thenReturn(userToTest);

            User user = sut.getLogin(usernameToTest,passwordToTest);

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
            String expectedSQL = "UPDATE users SET token = ? WHERE username = ?";

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            User user = new User(usernameToTest);
            sut.addTokenToDatabase(user);

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

    @Test
    public void selectUserFromTokenTest(){
        try {
            String expectedSQL = "SELECT * FROM users WHERE token = ?";
            String tokenToTest = "123";
            String expectedError = "Token is onjuist.";

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            Exception exception = assertThrows(badRequestException.class, () -> { sut.selectUserFromToken(tokenToTest);});

            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, tokenToTest);
            verify(preparedStatement).executeQuery();

            String actualError = exception.getMessage();

            assertTrue(actualError.contains(expectedError));
        }
        catch (Exception e){
            fail();
            e.getMessage();
        }
    }

    @Test
    public void selectUserFromTokenResultsTest(){
        try {
            String expectedSQL = "SELECT * FROM users WHERE token = ?";
            String usernameToTest = "Debbie";
            String tokenToTest = "123";

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);
            when(resultSet.getString("username")).thenReturn(usernameToTest);

            User user = sut.selectUserFromToken(tokenToTest);

            assertEquals(usernameToTest, user.getUsername());
        }
        catch (Exception e){
            fail();
            e.getMessage();
        }
    }

}
