package nl.han.oose.dea.interfaces;

import nl.han.oose.dea.domain.TracksContainer;

public interface ITrackDAO {
    TracksContainer findAvailableTracks(int playlistId);

    TracksContainer findFromPlaylistById(int id);

    void deleteFromPlaylist(int playlistId, int trackId);

    void addTrackToPlaylist(int playlistId, int trackId, boolean offlineAvailable);
}
