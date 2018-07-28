package android.mohamedalaa.com.displayerandoirdlibrary;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.EspressoException;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.udacity.gradle.builditbigger.MainActivity;

import org.hamcrest.Matchers;
import org.hamcrest.core.AllOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private IdlingResource idlingResource;

    @Rule
    public ActivityTestRule<MainActivity> ruleMainActivity
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        idlingResource = ruleMainActivity.getActivity()
                .getIdlingResource();

        IdlingRegistry.getInstance().register(idlingResource);

        // perform the click
        ruleMainActivity.getActivity().tellJoke(null);
    }

    @Test
    public void checkStringRetrievedFromApi(){
        // the joke should be anything except being empty.
        String emptyString = "";

        Espresso.onView(ViewMatchers.withId(R.id.jokeTextView))
                .check(ViewAssertions.matches(Matchers.not(ViewMatchers.withText(emptyString))));
    }

    @After
    public void unregisterIdlingResource(){
        if (idlingResource != null){
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }

}