package nl.han.oose.dea.interfaces;

import nl.han.oose.dea.domain.User;

public interface IUserDAO {
    User validateUser(String username, String password);
}
