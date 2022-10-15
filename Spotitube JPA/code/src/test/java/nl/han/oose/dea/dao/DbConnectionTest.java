package nl.han.oose.dea.dao;

import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class DbConnectionTest {
    @Test
    public void getInstanceTest() {
        //TEST
        DbConnection dbConnection = DbConnection.getInstance();

        //ASSERT
        assertNotNull(dbConnection);
    }

    @Test
    public void openConnectionTest() {
        //SETUP
        DbConnection dbConnection = DbConnection.getInstance();

        //TEST
        Connection conn = dbConnection.getConnection();

        //ASSERT
        assertNotNull(conn);
    }

    @Test
    public void openSecondConnectionTest() {
        //SETUP
        DbConnection dbConnection = DbConnection.getInstance();
        Connection conn = dbConnection.getConnection();

        //TEST
        Connection conn2 = dbConnection.getConnection();

        //ASSERT
        assertEquals(conn, conn2);
    }

    @Test
    public void closeConnectionTest() {
        //SETUP
        DbConnection dbConnection = DbConnection.getInstance();
        Connection conn = dbConnection.getConnection();
        dbConnection.closeConnection();

        //TEST
        Connection conn2 = dbConnection.getConnection();
        dbConnection.closeConnection();

        //ASSERT
        assertNotEquals(conn, conn2);
    }

}