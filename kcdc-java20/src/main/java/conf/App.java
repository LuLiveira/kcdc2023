package conf;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static conf.Constants.ATTENDEE_EMAIL;
import static conf.Constants.SPEAKER_EMAIL;

public class App {

    //FIX ME: 0. Replace redundant boxing
    public static final Integer ZERO_INTEGER = 0;

    public static void main(String[] args) {
        //FIX ME: 0. Uncomment/Comment below code.
        //showNullPointerException();

        Conference theConference = new Seeder().seed();

        System.out.println(theConference);

        System.out.println("Attendee email: ");
        System.out.println(ATTENDEE_EMAIL);
        System.out.println();
        System.out.println("Indented Speaker email: ");
        System.out.println(SPEAKER_EMAIL);

        //FIX ME: 1. Use var instead
        var shirtCountMap = determineShirtCount(theConference);
        displayShirtCounts(shirtCountMap);

        //FIX ME: 1. Use var instead with null assignment
        var hatCountMap = determineHatCount(theConference);
        displayHatCounts(hatCountMap);

        displayPaymentInvoicing(theConference);

        displayBadgeCount(theConference);

        displayRaffleWinners(theConference);

        displayMostVotedSession(theConference);
    }

    static Integer determineBadgeCount(Conference theConference) {
        int badgeCount = theConference.getStaff().size();
        badgeCount += theConference.getSpeakers().size();
        badgeCount += theConference.getVendorSponsors().size();
        badgeCount += theConference.getAttendees().size();
        return badgeCount;
    }

    static void displayBadgeCount(Conference theConference) {
        System.out.println("Total allowed people: " + determineBadgeCount(theConference));
    }

    static Map<String, Integer> determineShirtCount(Conference theConference) {
        Map<String, Integer> shirtCountMap = new HashMap<>();
        for (Speaker speaker : theConference.getSpeakers()) {
            String shirtSize = speaker.getShirtSize();
            shirtCountMap.putIfAbsent(shirtSize, ZERO_INTEGER);
            int currentCount = shirtCountMap.get(shirtSize);
            shirtCountMap.put(shirtSize, currentCount + 1);
        }
        return shirtCountMap;
    }

    static void displayShirtCounts(Map<String, Integer> shirtCountMap) {
        System.out.println("\nTotal number of shirts to order: " +
                shirtCountMap.values().stream().reduce(0, Integer::sum));

        for (String shirtSize : shirtCountMap.keySet()) {
            System.out.println("Shirt size: [" + shirtSize + "] -> Count: [" + shirtCountMap.get(shirtSize) + "]");
        }
    }

    static Map<String, Integer> determineHatCount(Conference theConference) {
        Map<String, Integer> hatCountMap = new HashMap<>();
        for (Staff staff : theConference.getStaff()) {
            String hatSize = staff.getHatSize();
            hatCountMap.putIfAbsent(hatSize, ZERO_INTEGER);
            int currentCount = hatCountMap.get(hatSize);
            hatCountMap.put(hatSize, currentCount + 1);
        }
        return hatCountMap;
    }

    static void displayHatCounts(Map<String, Integer> hatCountMap) {
        System.out.println("\nTotal number of hats to order: " +
                hatCountMap.values().stream().reduce(0, Integer::sum));

        for (String hatSize : hatCountMap.keySet()) {
            System.out.println("Hat size: [" + hatSize + "] -> Count: [" + hatCountMap.get(hatSize) + "]");
        }
    }

    static void displayPaymentInvoicing(Conference theConference) {
        double processingFee = 0.0D;
        for (Attendee attendee : theConference.getAttendees()) {
            // FIX ME: 2. Replace with switch expression
            processingFee += switch (attendee.getPaymentType()) {
                case AMEX -> 0.10D;
                case VISA, MASTERCARD -> 0.08D;
                case PAYPAL -> 0.11D;
            };
        }
        System.out.println("Total payment-processing fees: USD[" + String.format("%,.2f", processingFee) + "]");
    }

    static void displayRaffleWinners(Conference theConference) {
        List<String> allowedWinnerPool = new ArrayList<>();

        allowedWinnerPool.addAll(
                theConference.getAttendees().stream().map(Attendee::getUniqueId).toList());
        allowedWinnerPool.addAll(
                theConference.getVendorSponsors().stream().map(VendorSponsor::getUniqueId).toList());
        allowedWinnerPool.addAll(
                theConference.getSpeakers().stream().map(Speaker::getUniqueId).toList());
        int[] winners = {-1, -1, -1};
        for (int i = 0; i < winners.length; i++) {
            winners[i] = ThreadLocalRandom.current().nextInt(0, allowedWinnerPool.size());
        }
        while (winners[1] == winners[0]) {
            winners[1] = ThreadLocalRandom.current().nextInt(0, allowedWinnerPool.size());
        }
        while (winners[2] == winners[1] || winners[2] == winners[0]) {
            winners[2] = ThreadLocalRandom.current().nextInt(0, allowedWinnerPool.size());
        }

        AllowedPerson[] allowedPersonWinner = new AllowedPerson[3];

        for (int i = 0; i < winners.length; i++) {
            for (Attendee attendee : theConference.getAttendees()) {
                if (attendee.getUniqueId().equals(allowedWinnerPool.get(winners[i]))) {
                    allowedPersonWinner[i] = attendee;
                    break;
                }
            }
            for (Speaker speaker : theConference.getSpeakers()) {
                if (speaker.getUniqueId().equals(allowedWinnerPool.get(winners[i]))) {
                    allowedPersonWinner[i] = speaker;
                    break;
                }
            }
            for (VendorSponsor vendorSponsor : theConference.getVendorSponsors()) {
                if (vendorSponsor.getUniqueId().equals(allowedWinnerPool.get(winners[i]))) {
                    allowedPersonWinner[i] = vendorSponsor;
                    break;
                }
            }
        }

        //FIX ME: 4. Replace the below with a switch pattern-matched instanceof
        for (AllowedPerson allowedPerson : allowedPersonWinner) {
            switch (allowedPerson) {
                case Attendee attendee ->
                    System.out.println("Winner is an attendee: " +
                            attendee.getFirstName() + " " + attendee.getLastName() +
                            ", payment: " + attendee.getPaymentType());
                case Speaker speaker ->
                    System.out.println("Winner is a speaker: " +
                            speaker.getFirstName() + " " + speaker.getLastName() +
                            ", shirt size: " + speaker.getShirtSize());
                case VendorSponsor vendorSponsor ->
                    System.out.println("Winner is a vendor/sponsor: " +
                            vendorSponsor.getFirstName() + " " + vendorSponsor.getLastName() +
                            ", booth: " + vendorSponsor.getBoothName());
                default -> throw new IllegalStateException("Person not allowed: " + allowedPerson);
            }
        }

    }

    static void displayMostVotedSession(Conference theConference) {
        //FIX ME: 0. Replace to a Record getter and
        //          upgrade to a toList() instead of Collectors.toList()
        List<String> sessions = theConference.getSessions().stream().
                map(session -> session.sessionTitle()).toList();

        String mostVotedSessionTitle = sessions.get(
                ThreadLocalRandom.current().nextInt(0, sessions.size()));

        Optional<Session> sessionObject = theConference.getSessions().stream()
                .filter(session -> session.sessionTitle().equals(mostVotedSessionTitle))
                .findFirst();

        sessionObject.ifPresent(session -> displaySessionDetails(session));
    }

    //FIX ME: 8. Use a record pattern
    static void displaySessionDetails(Object object) {
        if (object instanceof Session(String title, String x, Speaker speaker)) {
            System.out.println("The most voted session: [" +
                    title +
                    "] by [" +
                    speaker.firstName +
                    " " +
                    speaker.lastName + "]");
        }
    }

    private static void showNullPointerException() {
        Conference fakeConference = new Conference("fake", "fake", Year.now(), "fake");
        Session session = new Session("fake", "fake", new Speaker(null, "fake", "fake"));
        Set<Session> sessions = Set.of(session);
        fakeConference.setSessions(sessions);
        Object aSpeakerFirstNameLength = ((Session) fakeConference.getSessions().toArray()[0]).mainSpeakerModerator().firstName.length();
    }
}
