package oose.dea.DAO;

import oose.dea.domain.Playlist;
import oose.dea.domain.Track;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrackDAO implements ITrackDAO{

    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    @Override
    public ArrayList<Track> getAllTracks(int playlist) {
        String tracksQuery = "select * from track where id NOT IN ( select trackId from songsinlist where playlistId = ?)";

        try(Connection connection = dataSource.getConnection()){

            PreparedStatement statement = connection.prepareStatement(tracksQuery);
            statement.setInt(1,playlist);
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
