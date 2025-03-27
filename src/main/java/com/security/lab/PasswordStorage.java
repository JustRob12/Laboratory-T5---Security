package com.security.lab;

import org.mindrot.jbcrypt.BCrypt;
import java.util.Arrays;

public class PasswordStorage {
    public static void main(String[] args) {
        // Use char[] instead of String for password
        char[] password = "P@ssw0rd".toCharArray();
        
        try {
            // Hash the password
            String hashedPassword = hashPassword(password);
            System.out.println("Password has been securely hashed");
            
            // Verify the password
            boolean passwordMatch = verifyPassword(password, hashedPassword);
            System.out.println("Password verification: " + passwordMatch);
            
        } finally {
            // Clear the password from memory
            Arrays.fill(password, '\0');
        }
    }
    
    public static String hashPassword(char[] password) {
        try {
            // Generate a salt and hash the password
            String salt = BCrypt.gensalt(12); // Work factor of 12
            return BCrypt.hashpw(new String(password), salt);
        } finally {
            // Clear the temporary String created for BCrypt
            // Note: This is a limitation of BCrypt's API which requires String
            Arrays.fill(password, '\0');
        }
    }
    
    public static boolean verifyPassword(char[] password, String hashedPassword) {
        try {
            // Verify the password against the hash
            return BCrypt.checkpw(new String(password), hashedPassword);
        } finally {
            // Clear the temporary String created for BCrypt
            Arrays.fill(password, '\0');
        }
    }
} 