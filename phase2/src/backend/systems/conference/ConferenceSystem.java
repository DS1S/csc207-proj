package backend.systems.conference;

import backend.systems.MenuSystem;
import backend.systems.events.EventSystem;
import frontend.MenuUI;

import java.util.List;

/**
 * An extension of the MenuSystem class that displays options for conferences.
 */
public class ConferenceSystem extends MenuSystem {
    private final List<EventSystem> eventSystems;
    private final ConferenceManager conferenceManager;
    private final MenuUI conferenceUI;

    /**
     * Constructs an instance of the ConferenceSystem class given an instance of eventSystems and a
     * conferenceManager.
     * @param eventSystems the eventSystems instance used by the conference system
     * @param conferenceManager the conference manager used by the system
     */
    public ConferenceSystem(List<EventSystem> eventSystems, ConferenceManager conferenceManager){
        super(conferenceManager.getNumberOfConferences() + 1);
        this.conferenceManager = conferenceManager;
        this.eventSystems = eventSystems;
        this.conferenceUI = new MenuUI();
    }

    /**
     * Overrides the displayOptions method by displaying conference names as options.
     */
    @Override
    protected void displayOptions() {
        conferenceUI.displayOptions(conferenceManager.getConferenceNames(), true);
    }

    /**
     * Overrides the processInput method.
     * @param index The input to be processed.
     */
    @Override
    protected void processInput(int index) {
        if (!(index - 1 >= eventSystems.size()))
            eventSystems.get(index - 1).run();
    }

    /**
     * Overrides the built-in toString method.
     * @return the string "Conferences"
     */
    @Override
    public String toString() {
        return "Conferences";
    }
}
