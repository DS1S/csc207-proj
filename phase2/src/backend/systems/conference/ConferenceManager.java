package backend.systems.conference;

import backend.entities.Conference;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConferenceManager implements Serializable {

    List<Conference> conferences;

    public ConferenceManager(){
        this.conferences = new ArrayList<>();
    }

    public boolean addConference(String conferenceName, LocalDate conferenceDate){
        for (Conference conf: conferences){
            if (conf.getConferenceName().equals(conferenceName)){
                return false;
            }
            if (conf.getConferenceDate().equals(conferenceDate)){
                return false;
            }
        }
        conferences.add(new Conference(conferenceName, conferenceDate));
        return true;
    }

    public boolean removeConference(String conferenceName){
        return conferences.removeIf(conference -> conference.getConferenceName().equals(conferenceName));
    }

    public List<String> getConferenceNames(){
        List<String> conferenceNames = new ArrayList<>();
        conferences.forEach(conference -> {
            conferenceNames.add(conference.getConferenceName() + " | " + conference.getConferenceDate());
        });
        return conferenceNames;
    }

    public int getNumberOfConferences(){
        return conferences.size();
    }





}
