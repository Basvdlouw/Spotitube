package nl.han.oose.dea.service;

import nl.han.oose.dea.domain.Playlist;
import nl.han.oose.dea.domain.PlaylistsContainer;
import nl.han.oose.dea.domain.Track;
import nl.han.oose.dea.domain.TracksContainer;
import nl.han.oose.dea.interfaces.IPlaylistDAO;
import nl.han.oose.dea.interfaces.IPlaylistService;
import nl.han.oose.dea.interfaces.ITrackDAO;
import org.apache.http.HttpStatus;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlaylistServiceTest {
    @Mock
    private IPlaylistDAO playlistDAO;

    @Mock
    private ITrackDAO trackDAO;

    @InjectMocks
    private IPlaylistService playlistService = new PlaylistService();

    @Test
    public void addPlaylistTest() {
        //SETUP
        JsonObject jsonObject = Json.createObjectBuilder().add("name", "TestName").build();
        List<Playlist> list = new ArrayList<>();
        list.add(new Playlist(1, "Bas", "Bas Playlist"));
        PlaylistsContainer playlistContainer = new PlaylistsContainer(list, 100);

        doNothing().when(playlistDAO).insert("TestName", "100");
        when(playlistDAO.findAll("100")).thenReturn(playlistContainer);

        //TEST
        Response response = playlistService.addPlaylist("100", jsonObject);

        //ASSERT
        verify(playlistDAO).insert("TestName", "100");
        verify(playlistDAO).findAll("100");
        assertThat(response.getStatus(), equalTo(HttpStatus.SC_OK));
        assertThat(response.getEntity(), instanceOf(PlaylistsContainer.class));
        assertThat(response.getEntity(), CoreMatchers.<Object>equalTo(playlistContainer));
    }

    @Test
    public void getAllPlaylistsFromUserTest() {
        //SETUP
        List<Playlist> list = new ArrayList<>();
        list.add(new Playlist(1, "Bas", "Bas Playlist"));
        PlaylistsContainer playlistsContainer = new PlaylistsContainer(list, 100);
        when(playlistDAO.findAll("100")).thenReturn(playlistsContainer);

        //TEST
        Response response = playlistService.getAllPlaylistsFromUser("100");

        //ASSERT
        verify(playlistDAO).findAll("100");
        assertThat(playlistDAO.findAll("100"), equalTo(playlistsContainer));
        assertThat(response.getStatus(), equalTo(HttpStatus.SC_OK));
        assertThat(response.getEntity(), instanceOf(PlaylistsContainer.class));
        assertThat(response.getEntity(), CoreMatchers.<Object>equalTo(playlistsContainer));
    }

    @Test
    public void deletePlaylistTest() {
        //SETUP
        List<Playlist> list = new ArrayList<>();
        list.add(new Playlist(1, "Bas", "Bas Playlist"));
        PlaylistsContainer playlistsContainer = new PlaylistsContainer(list, 100);
        when(playlistDAO.findAll("100")).thenReturn(playlistsContainer);
        doNothing().when(playlistDAO).delete(1);

        //TEST
        Response response = playlistService.deletePlaylist(1, "100");

        //ASSERT
        verify(playlistDAO).delete(1);
        verify(playlistDAO).findAll("100");
        assertThat(response.getStatus(), equalTo(HttpStatus.SC_OK));
        assertThat(response.getEntity(), instanceOf(PlaylistsContainer.class));
        assertThat(response.getEntity(), CoreMatchers.<Object>equalTo(playlistsContainer));
    }

    @Test
    public void editPlaylistNameTest() {
        //SETUP
        JsonObject jsonObject = Json.createObjectBuilder().add("name", "Death Metal").build();
        List<Playlist> list = new ArrayList<>();
        list.add(new Playlist(1, "Bas", "Bas Playlist"));
        PlaylistsContainer playlistsContainer = new PlaylistsContainer(list, 100);
        when(playlistDAO.findAll("100")).thenReturn(playlistsContainer);
        doNothing().when(playlistDAO).update(1, "Death Metal");

        //TEST
        Response response = playlistService.editPlaylistName(1, "100", jsonObject);


        //ASSERT
        verify(playlistDAO).update(1, "Death Metal");
        verify(playlistDAO).findAll("100");
        assertThat(response.getStatus(), equalTo(HttpStatus.SC_OK));
        assertThat(response.getEntity(), instanceOf(PlaylistsContainer.class));
        assertThat(response.getEntity(), CoreMatchers.<Object>equalTo(playlistsContainer));
    }

    @Test
    public void getTracksInPlaylistTest() {
        //SETUP
        List<Track> tracks = new ArrayList<>();
        tracks.add(new Track(1, "title", "queen", 100, "album", 100, "19-03-2019,", "description", true));
        TracksContainer tracksContainer = new TracksContainer(tracks);
        when(trackDAO.findFromPlaylistById(1)).thenReturn(tracksContainer);

        //TEST
        Response response = playlistService.getTracksInPlaylist(1, "100");

        //ASSERT
        verify(trackDAO).findFromPlaylistById(1);
        assertThat(response.getStatus(), equalTo(HttpStatus.SC_OK));
        assertThat(response.getEntity(), instanceOf(TracksContainer.class));
        assertThat(response.getEntity(), CoreMatchers.<Object>equalTo(tracksContainer));
    }

    @Test
    public void addTrackToPlaylistTest() {
        //SETUP
        JsonObject jsonObject = Json.createObjectBuilder().add("id", 1).add("offlineAvailable", true).build();
        List<Track> tracks = new ArrayList<>();
        tracks.add(new Track(1, "title", "queen", 100, "album", 100, "19-03-2019,", "description", true));
        TracksContainer tracksContainer = new TracksContainer(tracks);
        when(trackDAO.findFromPlaylistById(1)).thenReturn(tracksContainer);
        //TEST
        Response response = playlistService.addTrackToPlaylist(1, "100", jsonObject);

        //ASSERT
        verify(trackDAO).addTrackToPlaylist(1, 1, true);
        assertThat(response.getStatus(), equalTo(HttpStatus.SC_OK));
        assertThat(response.getEntity(), instanceOf(TracksContainer.class));
        assertThat(response.getEntity(), CoreMatchers.<Object>equalTo(tracksContainer));
    }

    @Test
    public void deleteTrackFromPlaylistTest() {
        //SETUP
        List<Track> tracks = new ArrayList<>();
        tracks.add(new Track(1, "title", "queen", 100, "album", 100, "19-03-2019,", "description", true));
        TracksContainer tracksContainer = new TracksContainer(tracks);
        when(trackDAO.findFromPlaylistById(1)).thenReturn(tracksContainer);

        //TEST
        Response response = playlistService.deleteTrackFromPlaylist(1, 1, "100");

        //ASSERT
        verify(trackDAO).deleteFromPlaylist(1, 1);
        verify(trackDAO).findFromPlaylistById(1);
        assertThat(trackDAO.findFromPlaylistById(1), equalTo(tracksContainer));
        assertThat(response.getStatus(), equalTo(HttpStatus.SC_OK));
        assertThat(response.getEntity(), instanceOf(TracksContainer.class));
        assertThat(response.getEntity(), CoreMatchers.<Object>equalTo(tracksContainer));
    }
}