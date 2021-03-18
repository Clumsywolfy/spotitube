package oose.dea.resource.DTO;

import oose.dea.domain.Track;

import java.util.ArrayList;

public class PlaylistDTO {
    public int id;
    public String name;
    public boolean owner;
    public ArrayList<Track> tracks;
}
