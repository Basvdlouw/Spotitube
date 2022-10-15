package nl.han.oose.dea.service;

import nl.han.oose.dea.domain.User;
import nl.han.oose.dea.interfaces.ILoginService;
import nl.han.oose.dea.interfaces.IUserDAO;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class LoginService implements ILoginService {

    @Inject
    private IUserDAO userDAO;

    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(JsonObject jsonObject) {
        User user = userDAO.validateUser(jsonObject.getString("user"), jsonObject.getString("password"));
        if (user != null) {
            return Response.ok().entity(user).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
