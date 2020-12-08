package backend.systems.events;

import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;
import frontend.EventUI;

import java.util.List;
import java.util.Map;

/**
 * A subsystem of the EventSystem that allows the user to perform actions related to the sign up of Events.
 */
class EventSignUpMenuSystem extends EventMenuSystem {
    /**
     * Constructs a new EventSignUpSystem with the given information.
     * @param eventManager The EventManager that will be used by the EventSignUpSystem.
     * @param userManager The UserManager that will be used by the EventSignUpSystem.
     * @param eventUI The EventUI that will be used by the EventSignUpSystem.
     */
    public EventSignUpMenuSystem(EventManager eventManager, UserManager userManager, EventUI eventUI) {
        super(eventManager, userManager, eventUI, 5);
    }

    /**
     * Tells the event UI to display options for signing up for events.
     */
    @Override
    protected void displayOptions() {
        eventUI.displaySignupOptions();
    }

    /**
     * Processes an integer input in the event sign up page.
     * @param index The input to be processed. 1 allows for event viewing, 2 allows for event sign up,
     *              3 allows a user to cancel registration, and 4 allows the user to view signed up events.
     */
    protected void processInput(int index) {
        switch (index) {
            case (1):
                eventUI.displayEvents(eventManager.retrieveAllEvents());
                break;
            case (2):
                SignUpForEvent();
                break;
            case (3):
                CancelSignUpForEvent();
                break;
            case (4):
                eventUI.displayEvents(eventManager.retrieveEventsByAttendee(userManager.getLoggedInUserUUID()));
                break;
        }
    }

    private void SignUpForEvent() {
        List<Map<String, Object>> eventList = eventManager.retrieveSignupAbleEvents(userManager.getLoggedInUserUUID());
        int index = processEvents(eventList) - 1;

        if(index != -1) {
            eventManager.registerAttendee(userManager.getLoggedInUserUUID(),index);
            eventUI.displaySignupSuccess();
        }
    }

    private void CancelSignUpForEvent() {
        List<Map<String, Object>> eventList = eventManager.retrieveEventsByAttendee(userManager.getLoggedInUserUUID());
        int index = processEvents(eventList) - 1;

        if (index != -1) {
            eventManager.removeAttendee(userManager.getLoggedInUserUUID(), index);
            eventUI.displayCancelSignupSuccess();
        }
    }

    /**
     * Overrides the built-in toString method.
     * @return the string "Event Sign Up"
     */
    @Override
    public String toString() {
        return "Event Sign Up";
    }
}
