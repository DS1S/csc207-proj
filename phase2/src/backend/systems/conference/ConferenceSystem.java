package backend.systems.conference;

import backend.systems.SubSystem;
import backend.systems.events.EventSystem;
import frontend.ConferenceUI;
import utility.inputprocessors.OptionIndexProcessor;

import java.util.List;
import java.util.Scanner;

public class ConferenceSystem extends SubSystem {

    private List<EventSystem> eventSystems;
    private ConferenceManager conferenceManager;
    private ConferenceUI conferenceUI;

    public ConferenceSystem(List<EventSystem> eventSystems, ConferenceManager conferenceManager){
        super(conferenceManager.getNumberOfConferences() + 1, new OptionIndexProcessor(new Scanner(System.in),
                conferenceManager.getNumberOfConferences() + 1));
        this.conferenceManager = conferenceManager;
        this.eventSystems = eventSystems;
        this.conferenceUI = new ConferenceUI();
    }

    @Override
    protected void displayOptions() {
        conferenceUI.displayConferences(conferenceManager.getConferenceNames());
    }

    @Override
    protected void processInput(int index) {
        if (!(index - 1 >= eventSystems.size()))
            eventSystems.get(index - 1).run();
    }

    @Override
    public String toString() {
        return "Conferences";
    }
}
