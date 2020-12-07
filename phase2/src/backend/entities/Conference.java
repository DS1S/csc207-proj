package backend.entities;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A conference with a name and specific date.
 */
public class Conference implements Serializable {
    private String conferenceName;
    private LocalDate conferenceDate;

    /**
     * Constructs a new conference object given a name and a date.
     * @param conferenceName name of the conference
     * @param conferenceDate date of the conference
     */
    public Conference(String conferenceName, LocalDate conferenceDate){
        this.conferenceName = conferenceName;
        this.conferenceDate = conferenceDate;
    }

    /**
     * Gets the name of the conference.
     * @return conference name
     */
    public String getConferenceName() {
        return conferenceName;
    }

    /**
     * Gets the date of the conference.
     * @return conference date
     */
    public LocalDate getConferenceDate(){
        return conferenceDate;
    }
}
