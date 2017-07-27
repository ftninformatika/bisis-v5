package com.ftninformatika.bisis.libenv;


import com.ftninformatika.bisis.librarian.Librarian;
import org.junit.jupiter.api.Test;
import retrofit2.Call;

import java.util.List;

/**
 * Created by Petar on 7/27/2017.
 */
public class LibEnvProxyTests {

    private final Call<List<Librarian>> callListLibrarianResponse;

    public LibEnvProxyTests() {
        callListLibrarianResponse = null;
    }

    @Test
    public void getAllLibrariansShouldReturnListOfLibrarians(){

    }

    @Test
    public void getAllLibrariansShouldReturnNull(){

    }

}
