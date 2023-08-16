package conf;

import java.util.UUID;

/**
 * Java has a problem with extremes when it comes to extension.
 * In the past, either a class was final (so no extension) or
 * "open" which implied infinite extensions, such as InvalidAttendee
 * here. With the more modern "sealed" classes it is possible to
 * create a more controlled and finite hierarchy for extensions.
 * In this example, Attendee, Speaker, Staff and VendorSponsor are
 * all allowed extensions, while InvalidAttendee needs to be
 * commented out.
 * <p>
 * NOTE: This class hierarchy shows the usage of sealed classes
 */
//FIX ME: 7. Convert to Sealed type
public sealed class AllowedPerson permits Attendee, Speaker, VendorSponsor, Staff {
    protected String firstName;
    protected String lastName;

    public AllowedPerson(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "AllowedPerson{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}

final class Attendee extends AllowedPerson {
    private final PaymentType paymentType;
    private final String uniqueId = UUID.randomUUID().toString();

    public Attendee(String firstName, String lastName, PaymentType paymentType) {
        super(firstName, lastName);
        this.paymentType = paymentType;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    @Override
    public String toString() {
        return "\n\tAttendee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", paymentType='" + paymentType + '\'' +
                '}';
    }
}

non-sealed class Speaker extends AllowedPerson {
    private final String shirtSize;
    private final String uniqueId = UUID.randomUUID().toString();

    public Speaker(String firstName, String lastName, String shirtSize) {
        super(firstName, lastName);
        this.shirtSize = shirtSize;
    }

    public String getShirtSize() {
        return shirtSize;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    @Override
    public String toString() {
        return "\n\tSpeaker{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", shirtSize='" + shirtSize + '\'' +
                '}';
    }
}

non-sealed class Staff extends AllowedPerson {
    private final String hatSize;

    public Staff(String firstName, String lastName, String hatSize) {
        super(firstName, lastName);
        this.hatSize = hatSize;
    }

    public String getHatSize() {
        return hatSize;
    }

    @Override
    public String toString() {
        return "\n\tStaff{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", hatSize='" + hatSize + '\'' +
                '}';
    }
}

non-sealed class VendorSponsor extends AllowedPerson {
    private final String boothName;
    private final String uniqueId = UUID.randomUUID().toString();

    public VendorSponsor(String firstName, String lastName, String boothName) {
        super(firstName, lastName);
        this.boothName = boothName;
    }

    public String getBoothName() {
        return boothName;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    @Override
    public String toString() {
        return "\n\tVendorSponsor{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", boothName='" + boothName + '\'' +
                '}';
    }
}

//FIX ME: 7. Show as not allowed in sealed hierarchy
//class InvalidAttendee extends AllowedPerson {
//    public InvalidAttendee(String firstName, String lastName) {
//        super(firstName, lastName);
//    }
//}

