package nl.han.oose.dea.interfaces;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/playlists")
public interface IPlaylistService {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response addPlaylist(@QueryParam("token") String token, JsonObject json);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllPlaylistsFromUser(@QueryParam("token") String token);

    @Path("{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    Response deletePlaylist(@PathParam("id") int playlistId, @QueryParam("token") String token);

    @Path("{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    Response editPlaylistName(@PathParam("id") int playlistId, @QueryParam("token") String token, JsonObject json);

    @Path("{id}/tracks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response getTracksInPlaylist(@PathParam("id") int playlistId, @QueryParam("token") String token);

    @Path("{id}/tracks")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response addTrackToPlaylist(@PathParam("id") int playlistId, @QueryParam("token") String token, JsonObject json);

    @Path("{playlistId}/tracks/{trackId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    Response deleteTrackFromPlaylist(@PathParam("playlistId") int playlistId, @PathParam("trackId") int trackId, @QueryParam("token") String token);
}
