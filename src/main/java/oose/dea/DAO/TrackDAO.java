package oose.dea.DAO;

import oose.dea.domain.Track;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class TrackDAO implements ITrackDAO{

    private Logger logger = Logger.getLogger(getClass().getName());

    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    @Override
    public ArrayList<Track> getAllTracks(int playlist, boolean isTrack) {
        String tracksQuery;
        if(isTrack) {
            tracksQuery = "SELECT * FROM track WHERE id NOT IN ( SELECT trackId FROM songsinlist WHERE playlistId = ?)";
        } else {
            tracksQuery = "SELECT * FROM track WHERE id IN ( SELECT trackId FROM songsinlist WHERE playlistId = ?)";
        }

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
            logger.severe(exception.getMessage());
        }
        return null;
    }
    public void setTrackAvailable(boolean offline, int id){
        String tracksQuery = "UPDATE track SET offlineAvailable = ? WHERE id = ?";

        try(Connection connection = dataSource.getConnection()){

            PreparedStatement statement = connection.prepareStatement(tracksQuery);
            statement.setBoolean(1, offline);
            statement.setInt(2, id);
            statement.executeUpdate();

        } catch(SQLException exception){
            logger.severe(exception.getMessage());
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
