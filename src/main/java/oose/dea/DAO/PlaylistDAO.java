package oose.dea.DAO;

import oose.dea.domain.Playlist;
import oose.dea.domain.Track;
import oose.dea.domain.User;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class PlaylistDAO implements IPlaylistDAO{

    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    @Override
    public ArrayList<Playlist> getAllPlaylists() {
        String playlistsQuery = "Select * from playlist";

        try(Connection connection = dataSource.getConnection()){

            PreparedStatement statement = connection.prepareStatement(playlistsQuery);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<Playlist> playlists = new ArrayList<>();

            while (resultSet.next()){
                Playlist playlist = new Playlist(resultSet.getInt("id"));
                playlist.setName(resultSet.getString("name"));
                playlist.setOwner(resultSet.getString("owner"));
                playlist.setTracks(new ArrayList<Track>());
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

    public ArrayList<Track> getAllPlaylistTracks(int id){
        String trackInPlaylistsQuery = "select * from track where id IN ( select trackId from songsinlist where playlistId = ?)";

        try(Connection connection = dataSource.getConnection()){

            PreparedStatement statement = connection.prepareStatement(trackInPlaylistsQuery);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<Track> tracks = new ArrayList<>();

            while (resultSet.next()){
                Track track = new Track(resultSet.getInt("id"));
                track.setTitle(resultSet.getString("title"));
                track.setPerformer(resultSet.getString("performer"));
                track.setDuration(resultSet.getInt("duration"));
                track.setAlbum(resultSet.getString("album"));
                track.setPlaycount(resultSet.getInt("playcount"));
                track.setPublicationDate(resultSet.getString("publicationDate"));
                track.setDescription(resultSet.getString("description"));
                track.setOfflineAvailable(resultSet.getBoolean("offlineAvailable"));
                tracks.add(track);
            }
            return tracks;

        } catch(SQLException exception){
            exception.printStackTrace();
        }
        return null;
    }
}
