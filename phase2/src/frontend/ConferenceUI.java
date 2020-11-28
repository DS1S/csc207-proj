package frontend;

import java.util.List;

public class ConferenceUI {

    public void displayConferences(List<String> conferenceNames){
        System.out.println("Please choose a conference or return to main menu.");
        for(int i = 0; i < conferenceNames.size(); i++){
            System.out.println((i + 1) + ". " + conferenceNames.get(i));
        }
        System.out.println(conferenceNames.size() + 1 + ": Return to main menu.");
    }
}
