package nl.han.oose.dea.dao;

import nl.han.oose.dea.domain.User;
import nl.han.oose.dea.interfaces.IUserDAO;

import javax.enterprise.inject.Default;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Default
public class UserDAO implements IUserDAO {



    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.validateUser("Bas", "test");

        System.out.println(user.getUser() + user.getUsername() + user.getPassword() + user.getToken());
    }
    @Override
    public User validateUser(String username, String password) {
        Connection conn = DbConnection.getInstance().getConnection();
        final String sqlQuery = "SELECT * FROM user WHERE username=? AND password=?";
        User user = null;
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                user = new User(rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("token"),
                        rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("User not found" + e);
        } finally {
            DbConnection.getInstance().closeConnection();
        }

        if (user != null) {
            return user;
        }
        return null;
    }
}
