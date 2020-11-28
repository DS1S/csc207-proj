package backend.systems.events.subsystems;

import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;
import frontend.EventUI;
import java.util.UUID;

public class EventCategoryRetrieverSubSystem extends EventSubSystem {
    /**
     * Constructs a new EventCategoryRetrieverSubSystem with the given information.
     * @param eventManager The EventManager that will be used by the EventCategoryRetrieverSubSystem.
     * @param userManager The UserManager that will be used by the EventCategoryRetrieverSubSystem.
     * @param eventUI The EventUI that will be used by the EventCategoryRetrieverSubSystem.
     * @param numOptions The number of menu options given by the EventCategoryRetrieverSubSystem.
     */
    public EventCategoryRetrieverSubSystem(EventManager eventManager, UserManager userManager, EventUI eventUI, int numOptions) {
        super(eventManager, userManager, eventUI, numOptions);
    }

    private UUID getSpeakerUUID() {
        String username = this.askForString("This speaker's username");
        UUID uuid = this.userManager.getUUIDWithUsername(username);
        while (uuid == null) {
            eventUI.displayInvalidSpeaker();
            username = this.askForString("This speaker's username");
            uuid = this.userManager.getUUIDWithUsername(username);
        }
        return uuid;
    }

    @Override
    protected void displayOptions() {
        eventUI.displayCategoryRetrieverOptions();
    }

    @Override
    protected void processInput(int index) {
        switch (index) {
            case(1):
                // TODO: Add event retrieval by time.
                break;
            case(2):
                eventUI.displayEvents(this.eventManager.retrieveEventsBySpeaker(this.getSpeakerUUID()));
                break;
            case(3):
                eventUI.displayEvents(this.eventManager.retrieveEventsByAttendee(this.userManager.getLoggedInUserUUID()));
                break;
        }
    }
}
