package tourguide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The tourGuide's main class.
 *
 * @author Unknown TourGuide Developer
 * @author Yahia CHERIFI
 */

@SpringBootApplication
public class Application {

    /**
     * The app's main method.
     * Responsible for starting the app
     * @param args args
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Class constructor.
     */
    protected Application() {
    }
}
