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

    int playlistIdToTest;
    String nameToTest;
    String ownerToTest;
    int lengthToTest;
    int trackIdToTest;

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
            String expectedSQL = "Select * from playlist";

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);

            when(resultSet.getInt("id")).thenReturn(playlistIdToTest);
            when(resultSet.getString("name")).thenReturn(nameToTest);
            when(resultSet.getString("owner")).thenReturn(ownerToTest);

            String expectedLengthSQL = "Select duration from track t inner join songsinlist s on t.id = s.trackId where playlistId = ?";

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
            String expectedSQL = "Select * from playlist";

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
            String expectedSQL = "Select duration from track t inner join songsinlist s on t.id = s.trackId where playlistId = ?";

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
            String expectedSQL = "delete from playlist where id = ? and owner = ?";

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
            String expectedSQL = "insert into playlist (name, owner) values (?,?)";

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
            String expectedSQL = "Update playlist Set name = ? Where id = ? and owner = ?";

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
            String expectedSQL = "delete s from songsinlist s inner join playlist p on p.id = s.playlistId where s.playlistId = ? and s.trackId = ? and p.owner = ?";

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
            String expectedSQL = "insert into songsinlist values (?,?)";

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

