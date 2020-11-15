package EventSystem.subsytems;

import EventSystem.Managers.EventManager;
import LoginSystem.UserManager;
import Presenters.EventUI;

/**
 * A subsystem of the EventSystem that allows the user to perform actions related to the viewing of Events.
 */
public class EventViewerSystem extends EventSubSystem {

    /**
     * Constructs a new EventViewerSystem with the given information.
     * @param eventManager the EventManager that will be used by the EventViewerSystem
     * @param userManager the UserManager that will be used by the EventViewerSystem
     * @param eventUI the EventUI that will be used by the EventViewerSystem
     * @param numOptions the number of menu options given by the EventViewerSystem
     */
    public EventViewerSystem(EventManager eventManager, UserManager userManager, EventUI eventUI, int numOptions) {
        super(eventManager, userManager, eventUI, numOptions);
    }

    @Override
    protected void displayOptions() {
        eventUI.displaySpeakerOptions();
    }

    @Override
    protected void processMainSignInput(int index) {
        switch (index) {
            case (1):
                eventUI.displayEvents(eventManager.retrieveAllEvents());
                break;
            case (2):
                eventUI.displayEvents(eventManager.retrieveEventsBySpeaker(userManager.getLoggedInUserUUID()));
                break;
        }
    }
}
