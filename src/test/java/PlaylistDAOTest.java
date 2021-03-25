import oose.dea.DAO.PlaylistDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class PlaylistDAOTest {

    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private PlaylistDAO playlistDAO;

    private int playlistIdToTest;
    private String nameToTest;
    private String ownerToTest;
    private int lengthToTest;
    private int trackIdToTest;
    private String expectedSQL;

    @BeforeEach
    public void setup() {
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        playlistDAO = new PlaylistDAO();
        playlistDAO.setDataSource(dataSource);

        playlistIdToTest = 1;
        nameToTest = "Metal";
        ownerToTest = "Debbie";
        lengthToTest = 100;
        trackIdToTest = 2;
    }

    @Test
    public void getAllPlaylistsResultTest() {
        try {
            expectedSQL = "SELECT * FROM playlist";

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);

            when(resultSet.getInt("id")).thenReturn(playlistIdToTest);
            when(resultSet.getString("name")).thenReturn(nameToTest);
            when(resultSet.getString("owner")).thenReturn(ownerToTest);

            String expectedLengthSQL = "SELECT duration FROM track t INNER JOIN songsinlist s ON t.id = s.trackId WHERE playlistId = ?";

            // instruct Mocks
            preparedStatement = mock(PreparedStatement.class);
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedLengthSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);

            when(resultSet.getInt("duration")).thenReturn(lengthToTest);

            playlistDAO.getAllPlaylists();

            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).executeQuery();

        } catch (Exception e) {
            fail();
            e.getMessage();
        }
    }

    @Test
    public void getAllPlaylistsTest() {
        try {
            expectedSQL = "SELECT * FROM playlist";

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            playlistDAO.getAllPlaylists();

            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).executeQuery();

        } catch (Exception e) {
            fail();
            e.getMessage();
        }
    }

    @Test
    public void calculatePlaylistLengthTest() {
        try {
            expectedSQL = "SELECT duration FROM track t INNER JOIN songsinlist s ON t.id = s.trackId WHERE playlistId = ?";

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);

            when(resultSet.getInt("duration")).thenReturn(lengthToTest);

            int length = playlistDAO.calculatePlaylistLength(playlistIdToTest);

            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setInt(1, playlistIdToTest);
            verify(preparedStatement).executeQuery();

            assertEquals(lengthToTest, length);

        } catch (Exception e) {
            fail();
            e.getMessage();
        }
    }

    @Test
    public void deletePlaylistTest () {
        try {
            expectedSQL = "DELETE FROM playlist WHERE id = ? AND owner = ?";

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            playlistDAO.deletePlaylist(playlistIdToTest,ownerToTest);

            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setInt(1, playlistIdToTest);
            verify(preparedStatement).setString(2,ownerToTest);
            verify(preparedStatement).executeUpdate();

        } catch (Exception e) {
            fail();
            e.getMessage();
        }
    }

    @Test
    public void addPlaylistTest () {
        try {
            expectedSQL = "INSERT INTO playlist (name, owner) VALUES (?,?)";

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            playlistDAO.addPlaylist(nameToTest,ownerToTest);

            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, nameToTest);
            verify(preparedStatement).setString(2,ownerToTest);
            verify(preparedStatement).executeUpdate();

        } catch (Exception e) {
            fail();
            e.getMessage();
        }
    }

    @Test
    public void editPlaylistTest () {
        try {
            expectedSQL = "UPDATE playlist SET name = ? WHERE id = ? AND owner = ?";

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            playlistDAO.editPlaylist(nameToTest, playlistIdToTest,ownerToTest);

            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, nameToTest);
            verify(preparedStatement).setInt(2, playlistIdToTest);
            verify(preparedStatement).setString(3,ownerToTest);
            verify(preparedStatement).executeUpdate();

        } catch (Exception e) {
            fail();
            e.getMessage();
        }
    }

    @Test
    public void deleteTrackFromPlaylistTest () {
        try {
            expectedSQL = "DELETE s FROM songsinlist s INNER JOIN playlist p ON p.id = s.playlistId WHERE s.playlistId = ? AND s.trackId = ? AND p.owner = ?";

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            playlistDAO.deleteTrackFromPlaylist(playlistIdToTest, trackIdToTest,ownerToTest);

            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setInt(1, playlistIdToTest);
            verify(preparedStatement).setInt(2, trackIdToTest);
            verify(preparedStatement).setString(3,ownerToTest);
            verify(preparedStatement).executeUpdate();

        } catch (Exception e) {
            fail();
            e.getMessage();
        }
    }

    @Test
    public void addTrackToPlaylistTest () {
        try {
            expectedSQL = "INSERT INTO songsinlist VALUES (?,?)";

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            playlistDAO.addTrackToPlaylist(playlistIdToTest, trackIdToTest);

            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setInt(1, playlistIdToTest);
            verify(preparedStatement).setInt(2, trackIdToTest);
            verify(preparedStatement).executeUpdate();

        } catch (Exception e) {
            fail();
            e.getMessage();
        }
    }
}

