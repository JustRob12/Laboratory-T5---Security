package com.security.lab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import org.owasp.encoder.Encode;
import java.util.logging.Logger;
import java.util.logging.Level;

public class DatabaseQuery {
    private static final String DB_URL = "jdbc:mysql://localhost/test?useSSL=true&serverTimezone=UTC";
    private static final Logger LOGGER = Logger.getLogger(DatabaseQuery.class.getName());
    private static final String QUERY = "SELECT username FROM users WHERE username = ?";
    
    public static void getUser(String username) throws SQLException {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DriverManager.getConnection(DB_URL);
            pstmt = conn.prepareStatement(QUERY);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String retrievedUsername = Encode.forHtml(rs.getString("username"));
                LOGGER.info(() -> "Found user: " + retrievedUsername);
            }
        } finally {
            closeQuietly(rs);
            closeQuietly(pstmt);
            closeQuietly(conn);
        }
    }
    
    private static void closeQuietly(AutoCloseable resource) {
        try {
            if (resource != null) {
                resource.close();
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error closing resource", e);
        }
    }
} 