package oose.dea.DAO;

import oose.dea.domain.Track;

import java.util.ArrayList;

public interface ITrackDAO {
    ArrayList<Track> getAllTracks(int playlist);
}
