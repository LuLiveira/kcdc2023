package conf;

//FIXME: 6. Convert to record
public class Session {
    private final String sessionTitle;
    private final String sessionAbstract;
    private final Speaker mainSpeakerModerator;

    public Session(String sessionTitle, String sessionAbstract, Speaker mainSpeakerModerator) {
        this.sessionTitle = sessionTitle;
        this.sessionAbstract = sessionAbstract;
        this.mainSpeakerModerator = mainSpeakerModerator;
    }

    public String getSessionTitle() {
        return sessionTitle;
    }

    public String getSessionAbstract() {
        return sessionAbstract;
    }

    public Speaker getMainSpeakerModerator() {
        return mainSpeakerModerator;
    }

    @Override
    public String toString() {
        return "\n\n\tSession{" +
                "sessionTitle='" + sessionTitle + '\'' +
                ", sessionAbstract='" + sessionAbstract + '\'' +
                ", mainSpeakerModerator=" + mainSpeakerModerator.lastName + ", " + mainSpeakerModerator.firstName +
                '}';
    }
}
