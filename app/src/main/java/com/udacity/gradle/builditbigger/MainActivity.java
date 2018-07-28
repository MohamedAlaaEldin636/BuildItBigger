package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.mohamedalaa.com.displayerandoirdlibrary.DisplayerMainActivity;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.gradle.builditbigger.utils.JokeEndPointsAsyncTask;
import com.udacity.gradle.builditbigger.utils.SimpleIdlingResource;


public class MainActivity extends AppCompatActivity implements
        JokeEndPointsAsyncTask.JokeEndPointsAsyncTaskListener {

    /**
     * Made so that, when we press on the button several times we only see the last toast,
     * so we don't get a lot of toasts which will take so much time for the user to wait
     * until it ends.
     */
    private Toast toast;

    /** to change the gravity of the toast textView only when needed which is once */
    private boolean gravityToastNeedsGravityChange = true;

    private SimpleIdlingResource idlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        // result should be handled by the provided listener here.

        if (idlingResource != null){
            idlingResource.setIdleState(false);
        }

        new JokeEndPointsAsyncTask(this).execute();
    }

    // ---- Private Helper Methods

    private void showToast(String msg){
        if (toast != null){
            toast.cancel();
        }

        toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);

        if (gravityToastNeedsGravityChange){
            View view = toast.getView();
            if (view != null && view instanceof LinearLayout){
                View childView;
                for (int i=0; i<((LinearLayout) view).getChildCount(); i++){
                    childView = ((LinearLayout) view).getChildAt(i);

                    if (childView instanceof TextView){
                        ((TextView) childView).setGravity(Gravity.CENTER);
                    }
                }
            }

            gravityToastNeedsGravityChange = false;
        }

        toast.show();
    }

    private void startDisplayerActivity(String jokeString){
        //showToast(jokeString);

        Intent displayerIntent = new Intent(this, DisplayerMainActivity.class);
        displayerIntent.putExtra(DisplayerMainActivity.INTENT_KEY_JOKE_STRING, jokeString);
        startActivity(displayerIntent);
    }

    // ---- JokeEndPointsAsyncTaskListener Interface Implemented Methods

    @Override
    public void onJokeStringReceived(String jokeString) {
        startDisplayerActivity(jokeString);

        if (idlingResource != null){
            idlingResource.setIdleState(true);
        }
    }

    // ---- For Testing

    @VisibleForTesting
    public IdlingResource getIdlingResource(){
        if (idlingResource == null){
            idlingResource = new SimpleIdlingResource();
        }

        return idlingResource;
    }
}
