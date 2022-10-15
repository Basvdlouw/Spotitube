package nl.han.oose.dea.dao;

import nl.han.oose.dea.domain.Track;
import nl.han.oose.dea.domain.TracksContainer;
import nl.han.oose.dea.interfaces.ITrackDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO implements ITrackDAO {

    @Override
    public TracksContainer findAvailableTracks(int playlistId) {
        Connection conn = DbConnection.getInstance().getConnection();
        final String sqlQuery = "SELECT * FROM track \n" +
                "WHERE track.id NOT IN \n" +
                "(SELECT track.id FROM track \n" +
                "INNER JOIN playlisttracks ON track.id = playlisttracks.trackId \n" +
                "WHERE playlisttracks.playlistId = ?);";
        List<Track> trackList = new ArrayList<>();

        ResultSet rs;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, playlistId);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                trackList.add(new Track(rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("performer"),
                        rs.getInt("duration"),
                        rs.getString("album"),
                        rs.getInt("playcount"),
                        rs.getString("publicationDate"),
                        rs.getString("description"),
                        false));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbConnection.getInstance().closeConnection();
        }
        TracksContainer tracksContainer = new TracksContainer(trackList);
        return tracksContainer;
    }

    @Override
    public TracksContainer findFromPlaylistById(int playlistId) {
        Connection conn = DbConnection.getInstance().getConnection();
        final String sqlQuery = "SELECT * FROM track \n" +
                "INNER JOIN playlisttracks ON track.id = playlisttracks.trackId \n" +
                "WHERE playlisttracks.playlistId = ?";
        List<Track> trackList = new ArrayList<>();
        PreparedStatement preparedStatement;
        ResultSet rs;
        try {
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, playlistId);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                trackList.add(new Track(rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("performer"),
                        rs.getInt("duration"),
                        rs.getString("album"),
                        rs.getInt("playcount"),
                        rs.getString("publicationDate"),
                        rs.getString("description"),
                        rs.getBoolean("offlineAvailable")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbConnection.getInstance().closeConnection();
        }
        TracksContainer tracksContainer = new TracksContainer(trackList);
        return tracksContainer;
    }

    @Override
    public void deleteFromPlaylist(int playlistId, int trackId) {
        Connection conn = DbConnection.getInstance().getConnection();
        final String sqlQuery = "DELETE FROM playlisttracks WHERE playlistId = ? AND trackId = ?;";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, playlistId);
            preparedStatement.setInt(2, trackId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbConnection.getInstance().closeConnection();
        }
    }

    @Override
    public void addTrackToPlaylist(int playlistId, int trackId, boolean offlineAvailable) {
        Connection conn = DbConnection.getInstance().getConnection();
        final String sqlQuery = "INSERT INTO playlisttracks (playlistId, trackId, offlineAvailable) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, playlistId);
            preparedStatement.setInt(2, trackId);
            preparedStatement.setBoolean(3, offlineAvailable);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbConnection.getInstance().closeConnection();
        }
    }
}
