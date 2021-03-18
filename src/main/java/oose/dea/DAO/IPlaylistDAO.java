package oose.dea.DAO;

import oose.dea.domain.Playlist;

import java.util.ArrayList;

public interface IPlaylistDAO {
     ArrayList<Playlist> getAllPlaylists();
     void deletePlaylist(int id);
     void addPlaylist(String name, String owner);
     void editPlaylist(String name, int id);
}
