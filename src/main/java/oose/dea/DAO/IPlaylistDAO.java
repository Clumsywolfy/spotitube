package oose.dea.DAO;

import java.util.ArrayList;

public interface IPlaylistDAO {
     ArrayList getAllPlaylists();
     void deletePlaylist(int id, String owner);
     void addPlaylist(String name, String owner);
     void editPlaylist(String name, int id, String user);
     void deleteTrackFromPlaylist(int playlistId, int trackId, String owner);
     void addTrackToPlaylist(int playlistId, int trackId);
}
