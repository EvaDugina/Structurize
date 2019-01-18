package com.blabla.structurize;

/**
 * Created by John on 13.11.2018.
 */

public class TextUtil {

    public static boolean validateEmail(String email) {
        String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        return email.matches(EMAIL_REGEX);
    }

    public static boolean validatePassword(String password){
        return hasUpperCase(password) && hasLowerCase(password) && hasNumber(password) && (password.length()>6);
    }

    public static boolean hasUpperCase(String password){
        for (int i = 0; i < password.length(); i++) {
            if(Character.isUpperCase(password.charAt(i)))
                return true;
        }
        return false;
    }
    public static boolean hasLowerCase(String password){
        for (int i = 0; i < password.length(); i++) {
            if(Character.isLowerCase(password.charAt(i)))
                return true;
        }
        return false;
    }
    public static boolean hasNumber(String password){
        for (int i = 0; i < password.length(); i++) {
            if(Character.isDigit(password.charAt(i)))
                return true;
        }
        return false;
    }

}
