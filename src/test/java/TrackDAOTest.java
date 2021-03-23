import oose.dea.DAO.TrackDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class TrackDAOTest {

    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private TrackDAO trackDAOMock;

    int playlistToTest;
    int trackIdToTest;
    boolean isTrackToTest;
    boolean isPlaylistToTest;
    boolean offlineToTest;

    @BeforeEach
    public void setup(){
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        trackDAOMock = new TrackDAO();
        trackDAOMock.setDataSource(dataSource);

        playlistToTest = 1;
        trackIdToTest = 90;
        isTrackToTest = true;
        isPlaylistToTest = false;
        offlineToTest = true;
    }

    @Test
    public void getAllTracksTrackTest(){
        try {
            String expectedSQL = "SELECT * FROM track WHERE id NOT IN ( SELECT trackId FROM songsinlist WHERE playlistId = ?)";

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);

            trackDAOMock.getAllTracks(playlistToTest, isTrackToTest);

            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setInt(1, playlistToTest);
            verify(preparedStatement).executeQuery();

        }
        catch (Exception e){
            fail();
            e.getMessage();
        }
    }

    @Test
    public void getAllTracksPlaylistTest(){
        try {
            String expectedSQL = "SELECT * FROM track WHERE id IN ( SELECT trackId FROM songsinlist WHERE playlistId = ?)";

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            trackDAOMock.getAllTracks(playlistToTest, isPlaylistToTest);

            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setInt(1, playlistToTest);
            verify(preparedStatement).executeQuery();

        }
        catch (Exception e){
            fail();
            e.getMessage();
        }
    }

    @Test
    public void setTrackAvailableTest(){
        try {
            String expectedSQL = "UPDATE track SET offlineAvailable = ? WHERE id = ?";

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            trackDAOMock.setTrackAvailable(offlineToTest, trackIdToTest);

            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setBoolean(1, offlineToTest);
            verify(preparedStatement).setInt(2, trackIdToTest);
            verify(preparedStatement).executeUpdate();

        }
        catch (Exception e){
            fail();
            e.getMessage();
        }
    }
}
