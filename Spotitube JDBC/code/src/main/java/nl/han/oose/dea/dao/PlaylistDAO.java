package nl.han.oose.dea.dao;

import nl.han.oose.dea.domain.Playlist;
import nl.han.oose.dea.domain.PlaylistsContainer;
import nl.han.oose.dea.interfaces.IPlaylistDAO;
import nl.han.oose.dea.interfaces.ITrackDAO;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Default
public class PlaylistDAO implements IPlaylistDAO {

    @Inject
    private ITrackDAO trackDAO;

    @Override
    public PlaylistsContainer findAll(String token) {
        Connection conn = DbConnection.getInstance().getConnection();
        final String sqlQuery = "SELECT playlist.owner, playlist.name, playlist.id \n" +
                "FROM Playlist INNER JOIN User \n" +
                "ON Playlist.owner = User.username \n" +
                "WHERE User.token= ?";
        List<Playlist> playlistList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                playlistList.add(new Playlist(resultSet.getInt("id"),
                        resultSet.getString("owner"),
                        resultSet.getString("name"),
                        trackDAO.findFromPlaylistById(resultSet.getInt("id"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbConnection.getInstance().closeConnection();
        }
        int lengte = 0;
        for (Playlist playlist : playlistList) {
            lengte += playlist.getLength();
        }
        PlaylistsContainer playlistsContainer = new PlaylistsContainer(playlistList, lengte);
        return playlistsContainer;
    }

    @Override
    public void insert(String name, String token) {
        Connection conn = DbConnection.getInstance().getConnection();
        final String sqlQuery = "INSERT INTO Playlist (playlist.id, playlist.name, playlist.owner)\n" +
                "SELECT playlist.id+1, ?, user.username\n" +
                "FROM Playlist, User\n" +
                "WHERE playlist.id=(SELECT max(id) FROM Playlist)\n" +
                "AND user.username=(SELECT user.username FROM User WHERE user.token = ?);";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, token);
            preparedStatement.executeUpdate();
            System.out.println("playlist added to the database");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbConnection.getInstance().closeConnection();
        }
    }

    @Override
    public void delete(int playlistId) {
        Connection conn = DbConnection.getInstance().getConnection();
        final String sqlQuery = "DELETE FROM Playlist WHERE id = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, playlistId);
            preparedStatement.executeUpdate();
            System.out.println("playlist deleted from the database");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbConnection.getInstance().closeConnection();
        }
    }

    @Override
    public void update(int playlistId, String name) {
        Connection conn = DbConnection.getInstance().getConnection();
        final String sqlQuery = "UPDATE Playlist SET name = ? WHERE id = ?;";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, playlistId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbConnection.getInstance().closeConnection();
        }
    }
}
