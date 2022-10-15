package nl.han.oose.dea.domain;

import java.util.List;

public class PlaylistsContainer {
   private List<Playlist> playlists;
   private int length;

    public PlaylistsContainer(List<Playlist> playlists, int length) {
        this.playlists = playlists;
        this.length = length;
    }
}
