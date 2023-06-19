package conf;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static conf.Constants.ATTENDEE_EMAIL;
import static conf.Constants.SPEAKER_EMAIL;

public class App {

    //FIXME: 0. Replace redundant boxing
    public static final Integer ZERO_INTEGER = new Integer(0);

    public static void main(String[] args) {
        //FIXME: 5. Uncomment/Comment below code.
        //showNullPointerException();

        Conference theConference = new Seeder().seed();

        System.out.println(theConference);

        System.out.println("Attendee email: ");
        System.out.println(ATTENDEE_EMAIL);
        System.out.println();
        System.out.println("Indented Speaker email: ");
        System.out.println(SPEAKER_EMAIL);

        //FIXME: 1. Use var instead
        Map<String, Integer> shirtCountMap = determineShirtCount(theConference);
        displayShirtCounts(shirtCountMap);

        //FIXME: 1. Use var instead with null assignment
        Map<String, Integer> hatCountMap = null;
        hatCountMap = determineHatCount(theConference);
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
            // FIXME: 2. Replace with switch expression
            switch (attendee.getPaymentType()) {
                case AMEX:
                    processingFee += 0.10D;
                    break;
                case VISA:
                case MASTERCARD:
                    processingFee += 0.08D;
                    break;
                case PAYPAL:
                    processingFee += 0.11D;
                    break;
            }
        }
        System.out.println("Total payment-processing fees: USD[" + String.format("%,.2f", processingFee) + "]");
    }

    static void displayRaffleWinners(Conference theConference) {
        List<String> allowedWinnerPool = new ArrayList<>();

        allowedWinnerPool.addAll(
                theConference.getAttendees().stream().map(Attendee::getUniqueId).collect(Collectors.toList()));
        allowedWinnerPool.addAll(
                theConference.getSpeakers().stream().map(Speaker::getUniqueId).collect(Collectors.toList()));
        allowedWinnerPool.addAll(
                theConference.getVendorSponsors().stream().map(VendorSponsor::getUniqueId).collect(Collectors.toList()));
        int[] winners = {-1, -1, -1};
        int winner1 = ThreadLocalRandom.current().nextInt(0, allowedWinnerPool.size());
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

                boolean allowedPersonFound = false;
        //FIXME: 4. Replace the below with a switch pattern-matched instanceof
        for (AllowedPerson allowedPerson : allowedPersonWinner) {
            if (allowedPerson instanceof Attendee) {
                Attendee attendee = (Attendee) allowedPerson;
                System.out.println("Winner is an attendee: " +
                        attendee.getFirstName() + " " + attendee.getLastName() +
                        ", payment: " + attendee.getPaymentType());
                allowedPersonFound = true;
            }
            if (allowedPerson instanceof Speaker) {
                Speaker speaker = (Speaker) allowedPerson;
                System.out.println("Winner is a speaker: " +
                        speaker.getFirstName() + " " + speaker.getLastName() +
                        ", shirt size: " + speaker.getShirtSize());
                allowedPersonFound = true;
            }
            if (allowedPerson instanceof VendorSponsor) {
                VendorSponsor vendorSponsor = (VendorSponsor) allowedPerson;
                System.out.println("Winner is a vendor/sponsor: " +
                        vendorSponsor.getFirstName() + " " + vendorSponsor.getLastName() +
                        ", booth: " + vendorSponsor.getBoothName());
                allowedPersonFound = true;
            }
            if(!allowedPersonFound) {
                throw new IllegalStateException("Person not allowed: " + allowedPerson);
            }
        }
    }

    static void displayMostVotedSession(Conference theConference) {
        //FIXME: 6. Replace to a Record getter and
        //          upgrade to a toList() instead of Collectors.toList()
        List<String> sessions = theConference.getSessions().stream().
                map(session -> session.getSessionTitle()).collect(Collectors.toList());

        String mostVotedSessionTitle = sessions.get(
                ThreadLocalRandom.current().nextInt(0, sessions.size()));

        //FIXME: 6. Replace to a Record getter
        Optional<Session> sessionObject = theConference.getSessions().stream()
                .filter(session -> session.getSessionTitle().equals(mostVotedSessionTitle))
                .findFirst();

        sessionObject.ifPresent(session -> displaySessionDetails(session));
    }

    //FIXME: 8. Use a record pattern
    static void displaySessionDetails(Object object) {
        if (object instanceof Session) {
            Session session = (Session) object;

            String title = session.getSessionTitle();
            Speaker speaker = session.getMainSpeakerModerator();

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
        Set<Session> sessions = new HashSet<>();
        sessions.add(session);
        fakeConference.setSessions(sessions);
        Object aSpeakerFirstNameLength = ((Session) fakeConference.getSessions().toArray()[0]).getMainSpeakerModerator().firstName.length();
    }
}
