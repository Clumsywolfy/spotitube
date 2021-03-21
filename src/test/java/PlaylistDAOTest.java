import oose.dea.DAO.PlaylistDAO;
import oose.dea.domain.Playlist;
import oose.dea.domain.User;
import oose.dea.exceptions.unauthorizedUserException;
import oose.dea.resource.DTO.PlaylistDTO;
import oose.dea.resource.DTO.PlaylistsDTO;
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

public class PlaylistDAOTest {

    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private PlaylistDAO playlistDAOMock;

    @BeforeEach

    public void setup() {
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        playlistDAOMock = new PlaylistDAO();
        playlistDAOMock.setDataSource(dataSource);
    }

    @Test
    public void getAllPlaylistsTest() {
        try {
            String expectedSQL = "Select * from playlist";
            int idToTest = 1;
            String nameToTest = "Metal";
            String ownerToTest = "Debbie";
            int lengthToTest = 100;

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);
            when(resultSet.getInt("id")).thenReturn(idToTest);
            when(resultSet.getString("name")).thenReturn(nameToTest);
            when(resultSet.getString("owner")).thenReturn(ownerToTest);

            ArrayList<Playlist> playlists = playlistDAOMock.getAllPlaylists();

        } catch (Exception e) {
            fail();
            e.getMessage();
        }
    }
}

