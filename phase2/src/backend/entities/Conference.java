package backend.entities;

import java.io.Serializable;
import java.time.LocalDate;

public class Conference implements Serializable {

    private String conferenceName;
    private LocalDate conferenceDate;

    public Conference(String conferenceName, LocalDate conferenceDate){
        this.conferenceName = conferenceName;
        this.conferenceDate = conferenceDate;
    }

    public String getConferenceName() {
        return conferenceName;
    }

    public LocalDate getConferenceDate(){
        return conferenceDate;
    }
}
