package com.udacity.gradle.builditbigger.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.jokesApi.JokesApi;
import com.udacity.gradle.builditbigger.backend.jokesApi.model.Joke;

import java.io.IOException;

import timber.log.Timber;

/**
 * Created by Mohamed on 7/28/2018.
 *
 * todo, maybe replace that pair along with context.
 */
public class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {

    private static JokesApi myApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if(myApiService == null) {  // Only do this once
            JokesApi.Builder builder = new JokesApi.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/");
                    /*.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });*/
            // end options for devappserver

            Timber.v("cb 1, builder == null -> " + (builder == null));

            myApiService = builder.build();

            Timber.v("cb 2, myApiService == null -> " + (myApiService == null));
        }

        context = params[0].first;
        String name = params[0].second;

        try {
            //myApiService.tellJoke().exe

            JokesApi.TellJoke tellJoke = myApiService.tellJoke();

            Timber.v("TellJoke -> " + (tellJoke == null));

            Timber.v("Dwedwedw");

            Joke joke = tellJoke.execute();
            //com.udacity.gradle.builditbigger.backend.Joke joke2 = myApiService.tellJoke().execute();

            Timber.v("joke is null ==> " + (joke == null));
            Timber.v("joke.getJoke() -> " + (joke.getJoke()));

            return joke.getJoke();
        } catch (IOException e) {
            Timber.v(e.getMessage());
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}
