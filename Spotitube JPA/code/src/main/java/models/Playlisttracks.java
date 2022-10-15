package models;

import javax.persistence.*;

@Entity
public class Playlisttracks {
    private int playlisttracksId;
    private Byte offlineAvailable;
    private Playlist playlistByPlaylistId;

    @Id
    @Column(name = "playlisttracksId", nullable = false)
    public int getPlaylisttracksId() {
        return playlisttracksId;
    }

    public void setPlaylisttracksId(int playlisttracksId) {
        this.playlisttracksId = playlisttracksId;
    }

    @Basic
    @Column(name = "offlineAvailable", nullable = true)
    public Byte getOfflineAvailable() {
        return offlineAvailable;
    }

    public void setOfflineAvailable(Byte offlineAvailable) {
        this.offlineAvailable = offlineAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Playlisttracks that = (Playlisttracks) o;

        if (playlisttracksId != that.playlisttracksId) return false;
        if (offlineAvailable != null ? !offlineAvailable.equals(that.offlineAvailable) : that.offlineAvailable != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = playlisttracksId;
        result = 31 * result + (offlineAvailable != null ? offlineAvailable.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "playlistId", referencedColumnName = "id", nullable = false)
    public Playlist getPlaylistByPlaylistId() {
        return playlistByPlaylistId;
    }

    public void setPlaylistByPlaylistId(Playlist playlistByPlaylistId) {
        this.playlistByPlaylistId = playlistByPlaylistId;
    }
}
