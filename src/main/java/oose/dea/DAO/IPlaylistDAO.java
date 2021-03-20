package oose.dea.DAO;

import oose.dea.domain.Playlist;
import oose.dea.domain.Track;

import java.util.ArrayList;

public interface IPlaylistDAO {
     ArrayList<Playlist> getAllPlaylists();
     void deletePlaylist(int id, String owner);
     void addPlaylist(String name, String owner);
     void editPlaylist(String name, int id, String user);
     ArrayList<Track> getAllPlaylistTracks(int id);
}
