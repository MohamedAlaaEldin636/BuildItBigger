package com.udacity.gradle.builditbigger.utils;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.jokesApi.JokesApi;
import com.udacity.gradle.builditbigger.backend.jokesApi.model.Joke;

import java.io.IOException;

/**
 * Created by Mohamed on 7/28/2018.
 *
 */
public class JokeEndPointsAsyncTask extends AsyncTask<Void , Void , String> {

    private JokeEndPointsAsyncTaskListener listener;

    private static JokesApi jokesApi = null;

    public JokeEndPointsAsyncTask(JokeEndPointsAsyncTaskListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... voids) {
        if(jokesApi == null) {  // Only do this once
            JokesApi.Builder builder = new JokesApi.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            jokesApi = builder.build();
        }

        try {
            JokesApi.TellJoke tellJoke = jokesApi.tellJoke();

            Joke joke = tellJoke.execute();

            return joke.getJoke();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String string) {
        if (listener != null){
            listener.onJokeStringReceived(string);
        }
    }

    public interface JokeEndPointsAsyncTaskListener {
        void onJokeStringReceived(String jokeString);
    }

}
