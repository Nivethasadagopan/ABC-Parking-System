package com.userstore;

import java.util.HashMap;
import java.util.Map;

public class UserDatabase {

    // Temporary customer storage (email → password)
    private static final Map<String, String> USERS = new HashMap<>();

    // Temporary admin storage (email → password)
    private static final Map<String, String> ADMINS = new HashMap<>();

    // Temporary member storage (email → Member object)
    

    /**
     * Provides global access to our single list of users.
     */
    public static Map<String, String> getUsers() {
        return USERS;
    }

    /**
     * Provides global access to our single list of admins.
     */
    public static Map<String, String> getAdmins() {
        return ADMINS;
    }

    /**
     * Provides global access to our single list of members.
     */
 
 
}
