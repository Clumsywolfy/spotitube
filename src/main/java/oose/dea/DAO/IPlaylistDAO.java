package oose.dea.DAO;

import oose.dea.domain.Playlist;

import java.util.ArrayList;

public interface IPlaylistDAO {
    public ArrayList<Playlist> getAllPlaylists();
    public void deletePlaylist(int id);
    public void addPlaylist(String name, String owner);
}
