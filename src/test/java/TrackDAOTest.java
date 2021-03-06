import oose.dea.DAO.TrackDAO;
import oose.dea.domain.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class TrackDAOTest {

    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private TrackDAO sut;

    int playlistToTest;
    int trackIdToTest;
    boolean isTrackToTest;
    boolean isPlaylistToTest;
    boolean offlineToTest;
    String expectedSQL;

    @BeforeEach
    public void setup(){
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        sut = new TrackDAO();
        sut.setDataSource(dataSource);

        playlistToTest = 1;
        trackIdToTest = 90;
        isTrackToTest = true;
        isPlaylistToTest = false;
        offlineToTest = true;
    }

    @Test
    public void getAllTracksTrackTest(){
        try {
            expectedSQL = "SELECT * FROM track WHERE id NOT IN ( SELECT trackId FROM songsinlist WHERE playlistId = ?)";

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);
            when(resultSet.getInt("id")).thenReturn(trackIdToTest);

            ArrayList<Track> tracksTest = sut.getAllTracks(playlistToTest, isTrackToTest);

            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setInt(1, playlistToTest);
            verify(preparedStatement).executeQuery();

            assertEquals(trackIdToTest,tracksTest.get(0).getId());
        }
        catch (Exception e){
            fail();
            e.getMessage();
        }
    }

    @Test
    public void getAllTracksPlaylistTest(){
        try {
            expectedSQL = "SELECT * FROM track WHERE id IN ( SELECT trackId FROM songsinlist WHERE playlistId = ?)";

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            sut.getAllTracks(playlistToTest, isPlaylistToTest);

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
            expectedSQL = "UPDATE track SET offlineAvailable = ? WHERE id = ?";

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            sut.setTrackAvailable(offlineToTest, trackIdToTest);

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
