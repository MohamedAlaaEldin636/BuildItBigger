package android.mohamedalaa.com.displayerandoirdlibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Notes
 * 1- No need to set onSaveInstanceState as when a rotation occur we will get the result
 *      from the intent.
 */
public class DisplayerMainActivity extends AppCompatActivity {

    // --- Constants

    public static final String INTENT_KEY_JOKE_STRING = "INTENT_KEY_JOKE_STRING";

    // --- Private Variables

    private String jokeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayer_main);

        // receive joke from intent
        receiveIntentExtras();

        // setup xml views
        setupXmlViews();
    }

    private void receiveIntentExtras() {
        Intent intent = getIntent();
        if (intent != null
                && intent.hasExtra(INTENT_KEY_JOKE_STRING)){
            jokeString = intent.getStringExtra(INTENT_KEY_JOKE_STRING);
        }

        if (jokeString == null || jokeString.isEmpty()){
            jokeString = getString(R.string.error_joke_received);
        }
    }

    private void setupXmlViews() {
        TextView jokeTextView = findViewById(R.id.jokeTextView);
        jokeTextView.setText(jokeString);
    }

}
