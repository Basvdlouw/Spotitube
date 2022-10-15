package nl.han.oose.dea.interfaces;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tracks")
public interface ITrackService {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllTracks(@QueryParam("forPlaylist") int playlistId, @QueryParam("token") String token);
}
