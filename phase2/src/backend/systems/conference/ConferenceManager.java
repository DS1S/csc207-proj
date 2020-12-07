package backend.systems.conference;

import backend.entities.Conference;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A conference manager class, used to manage a collection of conferences.
 */
public class ConferenceManager implements Serializable {
    List<Conference> conferences;

    /**
     * Constructs a new instance of ConferenceManager, which maintains a list of existing conferences.
     */
    public ConferenceManager(){
        this.conferences = new ArrayList<>();
    }

    /**
     * Adds a conference to the list of conferences if no conference on the list has the same name
     * or date.
     * @param conferenceName name of the conference
     * @param conferenceDate date of the conference
     * @return true if the conference is successfully added to the conference list, false otherwise
     */
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

    /**
     * Gets the name of all conferences on the conference list.
     * @return a list of names of conferences on the conference list
     */
    public List<String> getConferenceNames(){
        List<String> conferenceNames = new ArrayList<>();
        conferences.forEach(conference -> {
            conferenceNames.add(conference.getConferenceName() + " | " + conference.getConferenceDate());
        });
        return conferenceNames;
    }

    /**
     * Gets the number of conferences on the conference list.
     * @return number of conferences on the conference list
     */
    public int getNumberOfConferences(){
        return conferences.size();
    }





}
