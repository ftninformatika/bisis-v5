package com.ftninformatika.bisis;

public class LibraryList {
    public static final String[] LIB_PREFIXES = {"bs", "gbns", "bgb"};

    public static boolean isValidUsername(String username){
        for (String s: LIB_PREFIXES)
            if (username.endsWith("@"+s))
                return true;
        return false;
    }
}
