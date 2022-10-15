package nl.han.oose.dea.service;

import nl.han.oose.dea.domain.PlaylistsContainer;
import nl.han.oose.dea.domain.TracksContainer;
import nl.han.oose.dea.interfaces.IPlaylistDAO;
import nl.han.oose.dea.interfaces.IPlaylistService;
import nl.han.oose.dea.interfaces.ITrackDAO;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class PlaylistService implements IPlaylistService {
    @Inject
    private IPlaylistDAO playlistDAO;
    @Inject
    private ITrackDAO trackDAO;


    private Response getAllPlaylists(String token) {
        PlaylistsContainer playlistsContainer = playlistDAO.findAll(token);
        return Response.ok().entity(playlistsContainer).build();
    }

    private Response getAllTracks(int playlistId) {
        TracksContainer tracksContainer = trackDAO.findFromPlaylistById(playlistId);
        return Response.ok().entity(tracksContainer).build();
    }

    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, JsonObject json) {
        playlistDAO.insert(json.getString("name"), token);
        return getAllPlaylists(token);
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylistsFromUser(@QueryParam("token") String token) {
        return getAllPlaylists(token);
    }

    @Override
    @Path("{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") int playlistId, @QueryParam("token") String token) {
        playlistDAO.delete(playlistId);
        return getAllPlaylists(token);
    }

    @Override
    @Path("{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPlaylistName(@PathParam("id") int playlistId, @QueryParam("token") String token, JsonObject json) {
        playlistDAO.update(playlistId, json.getString("name"));
        return getAllPlaylists(token);
    }

    @Override
    @Path("{id}/tracks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getTracksInPlaylist(@PathParam("id") int playlistId, @QueryParam("token") String token) {
        return getAllTracks(playlistId);
    }

    @Override
    @Path("{id}/tracks")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@PathParam("id") int playlistId, @QueryParam("token") String token, JsonObject json) {
        trackDAO.addTrackToPlaylist(playlistId, json.getInt("id"), json.getBoolean("offlineAvailable"));
        return getAllTracks(playlistId);
    }

    @Override
    @Path("{playlistId}/tracks/{trackId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTrackFromPlaylist(@PathParam("playlistId") int playlistId, @PathParam("trackId") int trackId, @QueryParam("token") String token) {
        trackDAO.deleteFromPlaylist(playlistId, trackId);
        return getAllTracks(playlistId);
    }
}
