package backend.systems.events;

import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;
import frontend.EventUI;
import utility.inputprocessors.EventFieldsProcessor;

import java.time.LocalTime;
import java.util.UUID;

/**
 * An extension of the EventMenuSystem used to retrieve event categories.
 */
class ScheduleRetrieverMenuSystem extends EventMenuSystem {
    /**
     * Constructs a new ScheduleRetrieverMenuSystem with the given information.
     * @param eventManager The EventManager that will be used by the ScheduleRetrieverMenuSystem.
     * @param userManager The UserManager that will be used by the ScheduleRetrieverMenuSystem.
     * @param eventUI The EventUI that will be used by the ScheduleRetrieverMenuSystem.
     */
    public ScheduleRetrieverMenuSystem(EventManager eventManager, UserManager userManager, EventUI eventUI) {
        super(eventManager, userManager, eventUI, 4);
    }

    private UUID getSpeakerUUID() {
        String username;
        UUID uuid;
        do{
            eventUI.displaySpeakerPrompt();
            username = this.askForString("Speaker's Username");
            uuid = this.userManager.getUUIDWithUsername(username);
        }while(uuid == null);
        return uuid;
    }

    /**
     * Overrides the displayOptions method, displays category retriever options.
     */
    @Override
    protected void displayOptions() {
        eventUI.displayCategoryRetrieverOptions();
    }

    /**
     * Overrides the processInput method.
     * @param index The input to be processed.
     */
    @Override
    protected void processInput(int index) {
        switch (index) {
            case(1):
                EventFieldsProcessor timeProcessor = new EventFieldsProcessor(input, eventUI);
                LocalTime startTime = timeProcessor.processTimeInput();
                LocalTime endTime = timeProcessor.processTimeInput();
                eventUI.displayEvents(this.eventManager.retrieveEventsByTimeInterval(startTime, endTime));
                break;
            case(2):
                eventUI.displayEvents(this.eventManager.retrieveEventsBySpeaker(this.getSpeakerUUID()));
                break;
            case(3):
                eventUI.displayEvents(this.eventManager.retrieveEventsByAttendee(this.userManager.getLoggedInUserUUID()));
                break;
        }
    }

    /**
     * Overrides the built-in toString method.
     * @return the string "Retrieve Events Based on Category"
     */
    @Override
    public String toString() {
        return "Retrieve Events Based on Category";
    }
}
