package backend.systems.events.subsystems;

import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;
import frontend.EventUI;

/**
 * A subsystem of the EventSystem that allows the user to perform actions related to the viewing of Events.
 */
class EventViewerMenuSystem extends EventMenuSystem {
    /**
     * Constructs a new EventViewerSystem with the given information.
     * @param eventManager The EventManager that will be used by the EventViewerSystem.
     * @param userManager The UserManager that will be used by the EventViewerSystem.
     * @param eventUI The EventUI that will be used by the EventViewerSystem.
     * @param numOptions The number of menu options given by the EventViewerSystem.
     */
    public EventViewerMenuSystem(EventManager eventManager, UserManager userManager, EventUI eventUI, int numOptions) {
        super(eventManager, userManager, eventUI, numOptions);
    }

    /**
     * Tells the event UI to display event options for speakers.
     */
    @Override
    protected void displayOptions() {
        eventUI.displaySpeakerOptions();
    }

    /**
     * Processes an integer input in the event viewing page.
     * @param index The inout to be processed. 1 allows for viewing of all events. 2 allows for
     *              displaying the events run by the user (assuming they are a speaker).
     */
    @Override
    protected void processInput(int index) {
        switch (index) {
            case (1):
                eventUI.displayEvents(eventManager.retrieveAllEvents());
                break;
            case (2):
                eventUI.displayEvents(eventManager.retrieveEventsBySpeaker(userManager.getLoggedInUserUUID()));
                break;
        }
    }

    @Override
    public String toString() {
        return "Event Viewer";
    }
}
