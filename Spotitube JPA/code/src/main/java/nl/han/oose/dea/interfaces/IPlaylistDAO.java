package nl.han.oose.dea.interfaces;

import nl.han.oose.dea.domain.PlaylistsContainer;

public interface IPlaylistDAO {
    PlaylistsContainer findAll(String token);

    void insert(String name, String token);

    void delete(int playlistId);

    void update(int playlistId, String name);
}
