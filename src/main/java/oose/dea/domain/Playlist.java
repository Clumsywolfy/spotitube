package oose.dea.domain;

import java.util.ArrayList;

public class Playlist {
    private int id;
    private String name;
    private boolean owner;
    private ArrayList<String> tracks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public ArrayList<String> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<String> tracks) {
        this.tracks = tracks;
    }
}
