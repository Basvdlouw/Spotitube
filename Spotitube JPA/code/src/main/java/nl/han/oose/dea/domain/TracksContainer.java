package nl.han.oose.dea.domain;

import java.util.List;

public class TracksContainer {
   private List<Track> tracks;

    public List<Track> getTracks() {
        return tracks;
    }

    public TracksContainer(List<Track> tracks) {
        this.tracks = tracks;
    }
}
