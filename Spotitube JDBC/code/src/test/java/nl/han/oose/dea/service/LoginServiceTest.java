package nl.han.oose.dea.service;

import nl.han.oose.dea.domain.User;
import nl.han.oose.dea.interfaces.ILoginService;
import nl.han.oose.dea.interfaces.IUserDAO;
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

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    @Mock
    private IUserDAO userDAO;

    @InjectMocks
    private ILoginService loginService = new LoginService();

    @Test
    public void checValidkUserTest() {
        //SETUP
        User user = new User("Bas", "test", "100", "Bas");
        JsonObject jsonObject = Json.createObjectBuilder().add("user", "Bas").add("password", "test").build();
        when(userDAO.validateUser("Bas", "test")).thenReturn(user);

        //TEST
        Response response = loginService.login(jsonObject);

        //ASSERT
        verify(userDAO).validateUser("Bas", "test");
        assertThat(response.getStatus(), equalTo(HttpStatus.SC_OK));
        assertThat(response.getEntity(), instanceOf(User.class));
        assertThat(response.getEntity(), CoreMatchers.<Object>equalTo(user));
    }

    @Test
    public void checkInvalidUserTest() {
        //SETUP
        JsonObject jsonObject = Json.createObjectBuilder().add("user", "Bas").add("password", "test").build();
        when(userDAO.validateUser("Bas", "test")).thenReturn(null);

        //TEST
        Response response = loginService.login(jsonObject);

        //ASSERT
        verify(userDAO).validateUser("Bas", "test");
        assertThat(response.getStatus(), equalTo(HttpStatus.SC_UNAUTHORIZED));
        assertThat(response.getEntity(), is(nullValue()));
    }
}
