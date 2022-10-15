package nl.han.oose.dea.service;

import nl.han.oose.dea.domain.TracksContainer;
import nl.han.oose.dea.interfaces.ITrackDAO;
import nl.han.oose.dea.interfaces.ITrackService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class TrackService implements ITrackService {
    @Inject
    private ITrackDAO trackDAO;

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracks(@QueryParam("forPlaylist") int playlistId, @QueryParam("token") String token) {
        TracksContainer tracksContainer = trackDAO.findAvailableTracks(playlistId);
        return Response.ok().entity(tracksContainer).build();
    }
}
