package conf;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Constants {

    //FIXME: 3. Replace with a text block
    public static final String ATTENDEE_EMAIL = "Welcome to this year's conference! " +
            "We're excited you are attending!!! " +
            "\nHere is a list of things to know." +
            "\n\tSchedule is located at: https://marsdev.io/schedule" +
            "\n\tSpeakers can be viewed at: https://marsdev.io/speakers" +
            "\n\tOther activities and raffle details are available at: https://mardev.io/foryou" +
            "\n\tCode of coduct: https://marsdev.io/code-of-conduct" +
            "\nWe really look forward to seeing you here and hope you get to both learn as well as enjoy our conference!";

    //FIXME: 3. Replace with indentation-based text block
    public static final String SPEAKER_EMAIL = "Welcome to this year's conference! " +
            "\tWe're grateful you are speaking!!! " +
            "\n\tHere is a list of things to know." +
            "\n\t\tSchedule is located at: https://marsdev.io/schedule" +
            "\n\t\tSpeakers can be viewed at: https://marsdev.io/speakers" +
            "\n\t\tSpeaker swag and speaker dinner (requires login): https://mardev.io/restricted/speaker-info" +
            "\n\t\tCode of coduct: https://marsdev.io/code-of-conduct" +
            "\nWe really look forward to seeing you here and hope you enjoy our conference!";


    public static final Set<String> SHIRT_SIZES;
    public static final Set<String> HAT_SIZES;

    static {
        SHIRT_SIZES = Stream.of("XS", "S", "M", "L", "XL", "2XL", "3XL", "4XL")
                .collect(Collectors.toCollection(HashSet::new));
        HAT_SIZES = Stream.of("S/M", "L", "XL")
                .collect(Collectors.toCollection(HashSet::new));
    }

    public static String randomShirtSize() {
        int i = ThreadLocalRandom.current().nextInt(0, SHIRT_SIZES.size());
        int counter = 0;
        for (String size : SHIRT_SIZES) {
            if (i == counter) {
                return size;
            }
            counter++;
        }
        return "";
    }

    public static String randomHatSize() {
        int i = ThreadLocalRandom.current().nextInt(0, HAT_SIZES.size());
        int counter = 0;
        for (String size : HAT_SIZES) {
            if (i == counter) {
                return size;
            }
            counter++;
        }
        return "";
    }
}
