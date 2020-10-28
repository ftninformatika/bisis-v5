package com.ftninformatika.bisisauthentication;

import org.springframework.stereotype.Component;

/**
 * Created by Petar on 6/20/2017.
 */
@Component("libraryPrefixProvider")
public class LibraryPrefixProvider {

    private static final ThreadLocal<String> prefix = new ThreadLocal<String>();

    public String getLibPrefix(){
        return prefix.get();
    }

    public static void setPrefix(String lib){
        prefix.set(lib);
    }
}
