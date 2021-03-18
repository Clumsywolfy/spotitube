package oose.dea.DAO;

import oose.dea.domain.Playlist;
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
                
            }
            return playlists;

        } catch(SQLException exception){
            exception.printStackTrace();
        }
        return null;
    }
}
