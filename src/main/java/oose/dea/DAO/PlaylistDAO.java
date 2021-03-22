package oose.dea.DAO;

import oose.dea.domain.Playlist;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistDAO implements IPlaylistDAO{

    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    @Override
    public ArrayList getAllPlaylists() {
        String playlistsQuery = "Select * from playlist";

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
            exception.printStackTrace();
        }
        return null;
    }

    public int calculatePlaylistLength(int id) {
        String songDurationQuery = "Select duration from track t inner join songsinlist s on t.id = s.trackId where playlistId = ?";

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
            exception.printStackTrace();
        }
        return 0;
    }

    public void deletePlaylist(int id, String owner){
        String deletePlaylistQuery = "delete from playlist where id = ? and owner = ?";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(deletePlaylistQuery);
            statement.setInt(1, id);
            statement.setString(2, owner);
            statement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void addPlaylist(String name, String owner){
        String addPlaylistQuery = "insert into playlist (name, owner) values (?,?)";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(addPlaylistQuery);
            statement.setString(1, name);
            statement.setString(2, owner);
            statement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void editPlaylist(String name, int id, String user) {
        String editPlaylistQuery = "Update playlist Set name = ? Where id = ? and owner = ?";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(editPlaylistQuery);
            statement.setString(1, name);
            statement.setInt(2, id);
            statement.setString(3, user);
            statement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void deleteTrackFromPlaylist(int playlistId, int trackId, String owner){
        String deletePlaylistQuery = "delete s from songsinlist s inner join playlist p on p.id = s.playlistId where s.playlistId = ? and s.trackId = ? and p.owner = ?";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(deletePlaylistQuery);
            statement.setInt(1, playlistId);
            statement.setInt(2, trackId);
            statement.setString(3, owner);
            statement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void addTrackToPlaylist(int playlistId, int trackId){
        String addPlaylistQuery = "insert into songsinlist values (?,?)";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(addPlaylistQuery);
            statement.setInt(1, playlistId);
            statement.setInt(2, trackId);
            statement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }
}
