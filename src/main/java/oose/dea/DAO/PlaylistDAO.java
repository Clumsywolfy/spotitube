package oose.dea.DAO;

import oose.dea.domain.Playlist;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class PlaylistDAO implements IPlaylistDAO{

    private Logger logger = Logger.getLogger(getClass().getName());

    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    @Override
    public ArrayList getAllPlaylists() {
        String playlistsQuery = "SELECT * FROM playlist";

        try(Connection connection = dataSource.getConnection()){

            PreparedStatement statement = connection.prepareStatement(playlistsQuery);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<Playlist> playlists = new ArrayList<>();

            while (resultSet.next()){
                Playlist playlist = new Playlist(resultSet.getInt("id"));
                playlist.setName(resultSet.getString("name"));
                playlist.setOwner(resultSet.getString("owner"));
                playlist.setTracks(new ArrayList<>());
                playlist.setLength(calculatePlaylistLength(resultSet.getInt("id")));

                playlists.add(playlist);
            }
            return playlists;

        } catch(SQLException exception){
            logger.severe(exception.getMessage());
        }
        return null;
    }

    public int calculatePlaylistLength(int id) {
        String songDurationQuery = "SELECT duration FROM track t INNER JOIN songsinlist s ON t.id = s.trackId WHERE playlistId = ?";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(songDurationQuery);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            int length = 0;

            while (resultSet.next()) {
                length += (resultSet.getInt("duration"));
            }
            return length;

        } catch (SQLException exception) {
            logger.severe(exception.getMessage());
        }
        return 0;
    }

    public void deletePlaylist(int id, String owner){
        String deletePlaylistQuery = "DELETE FROM playlist WHERE id = ? AND owner = ?";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(deletePlaylistQuery);
            statement.setInt(1, id);
            statement.setString(2, owner);
            statement.executeUpdate();

        } catch (SQLException exception) {
            logger.severe(exception.getMessage());
        }
    }

    public void addPlaylist(String name, String owner){
        String addPlaylistQuery = "INSERT INTO playlist (name, owner) VALUES (?,?)";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(addPlaylistQuery);
            statement.setString(1, name);
            statement.setString(2, owner);
            statement.executeUpdate();

        } catch (SQLException exception) {
            logger.severe(exception.getMessage());
        }
    }

    @Override
    public void editPlaylist(String name, int id, String user) {
        String editPlaylistQuery = "UPDATE playlist SET name = ? WHERE id = ? AND owner = ?";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(editPlaylistQuery);
            statement.setString(1, name);
            statement.setInt(2, id);
            statement.setString(3, user);
            statement.executeUpdate();

        } catch (SQLException exception) {
            logger.severe(exception.getMessage());
        }
    }

    @Override
    public void deleteTrackFromPlaylist(int playlistId, int trackId, String owner){
        String deletePlaylistQuery = "DELETE s FROM songsinlist s INNER JOIN playlist p ON p.id = s.playlistId WHERE s.playlistId = ? AND s.trackId = ? AND p.owner = ?";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(deletePlaylistQuery);
            statement.setInt(1, playlistId);
            statement.setInt(2, trackId);
            statement.setString(3, owner);
            statement.executeUpdate();

        } catch (SQLException exception) {
            logger.severe(exception.getMessage());
        }
    }

    @Override
    public void addTrackToPlaylist(int playlistId, int trackId){
        String addPlaylistQuery = "INSERT INTO songsinlist VALUES (?,?)";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(addPlaylistQuery);
            statement.setInt(1, playlistId);
            statement.setInt(2, trackId);
            statement.executeUpdate();

        } catch (SQLException exception) {
            logger.severe(exception.getMessage());
        }
    }

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }
}
