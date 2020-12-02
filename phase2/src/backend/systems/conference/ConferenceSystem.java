package backend.systems.conference;

import backend.systems.MenuSystem;
import backend.systems.events.EventSystem;
import frontend.MenuUI;

import java.util.List;

public class ConferenceSystem extends MenuSystem {

    private final List<EventSystem> eventSystems;
    private final ConferenceManager conferenceManager;
    private final MenuUI conferenceUI;

    public ConferenceSystem(List<EventSystem> eventSystems, ConferenceManager conferenceManager){
        super(conferenceManager.getNumberOfConferences() + 1);
        this.conferenceManager = conferenceManager;
        this.eventSystems = eventSystems;
        this.conferenceUI = new MenuUI();
    }

    @Override
    protected void displayOptions() {
        conferenceUI.displayOptions(conferenceManager.getConferenceNames());
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
