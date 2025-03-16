package com.portfolio.portfolio_v3.util;

import org.mindrot.jbcrypt.BCrypt;

public class SecurityUtil {

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
