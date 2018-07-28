package com.udacity.gradle.builditbigger.backend;

import android.mohamedalaa.com.jokes.JokeTeller;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
        name = "jokesApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "tellJoke")
    public Joke tellJoke(/*@Named("name") String name*/) {
        Joke joke = new Joke();
        joke.setJoke("Hi, " + "name" + "\ntest v1\n" + JokeTeller.getRandomJoke());

        return joke;
    }

}
