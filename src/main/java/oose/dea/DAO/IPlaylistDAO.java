package oose.dea.DAO;

import oose.dea.domain.Playlist;
import oose.dea.domain.Track;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IPlaylistDAO {
     ArrayList<Playlist> getAllPlaylists();
     void deletePlaylist(int id, String owner);
     void addPlaylist(String name, String owner);
     void editPlaylist(String name, int id, String user);
     ArrayList<Track> getAllPlaylistTracks(int id);
     void deleteTrackFromPlaylist(int playlistId, int trackId, String owner);
     void addTrackToPlaylist(int playlistId, int trackId);
     void setTrackAvailable(boolean offline, int id);
}
