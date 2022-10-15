package nl.han.oose.dea.domain;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private int id;
    private String name;
    private String owner;
    private TracksContainer tracksContainer;

    public Playlist(int id, String owner, String name, TracksContainer tracksContainer) {
        this.id = id;
        this.owner = owner;
        this.name = name;
       this.tracksContainer = tracksContainer;
    }

    public Playlist(int id, String owner, String name) {
        this.id = id;
        this.owner = owner;
        this.name = name;
    }

    private int length() {
        int duration = 0;
        for (Track track : tracksContainer.getTracks()) {
            duration += track.getDuration();
        }
        return duration;
    }

    public int getLength() {
        return length();
    }

    public TracksContainer getTracksContainer() {
        return tracksContainer;
    }
}


