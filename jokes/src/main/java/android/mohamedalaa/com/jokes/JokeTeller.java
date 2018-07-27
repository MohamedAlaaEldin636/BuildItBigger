package android.mohamedalaa.com.jokes;

import java.util.Random;

public class JokeTeller {

    public static String getRandomJoke(){
        Random random = new Random();
        int numBetweenOneAndTen = random.nextInt(10) + 1;
        String randomNumberString = String.valueOf(numBetweenOneAndTen);

        return "Random Joke #" + randomNumberString;
    }

}
