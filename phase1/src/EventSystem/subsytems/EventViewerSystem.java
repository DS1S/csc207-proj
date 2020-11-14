package EventSystem.subsytems;

import EventSystem.Managers.EventManager;
import LoginSystem.UserManager;
import Presenters.EventUI;

public class EventViewerSystem extends EventSubSystem {

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
            case (2):
                eventUI.displayEvents(eventManager.retrieveEventsBySpeaker(userManager.getLoggedInUserUUID()));
        }
    }
}
