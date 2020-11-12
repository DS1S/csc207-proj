package SchedulingSystem;

import coreUtil.IRunnable;
import java.util.UUID;
import Presenters.EventUI;

public class EventSystem implements IRunnable {
    private EventManager eventManager;
    private EventUI eventUI;

    @Override
    public void run() {
    }

    @Override
    public String toString() {
        return "Events";
    }

    /**
     * Call the EventUI presenter to show message to use whether the use has successfully registered to an event or not
     * Adds the new attendee to the Event iff there are no conflicting problems (Double-Booking or Full).
     *
     * @param attendee the UUID of the attendee to be removed
     * @param index the index of the Event, relative to the list of the events that the given attendee is signed up for
     * @param title the title of the new Event
     * @param speakerUUID the UUID of the speaker of the new Event
     */

    public void SignUpforEvent(int index, UUID attendee, String title, UUID speakerUUID) {
        if (eventManager.isEventatCapacity(index)) {
            eventUI.displaySignUpFull();
        }
        else {
            if (eventManager.retrieveAttendees(title, speakerUUID).contains(attendee)) {
                eventUI.displaySignUpDouble();
            }
            else {
                eventManager.registerAttendee(attendee,index);
                eventUI.displaySignupSuccess();
            }
        }
    }

    /**
     * Call the EventUI presenter to show message to use whether the use has successfully unregistered
     * to an event or not
     *
     * Adds the new attendee to the Event iff there are no conflicting problems. (Already Cancel)
     *
     * @param attendee the UUID of the attendee to be removed
     * @param index the index of the Event, relative to the list of the events that the given attendee is signed up for
     * @param title the title of the new Event
     * @param speakerUUID the UUID of the speaker of the new Event
     */

    public void CancelSignUpforEvent(int index, UUID attendee, String title, UUID speakerUUID) {
        if (eventManager.retrieveAttendees(title, speakerUUID).contains(attendee)) {
            eventManager.removeAttendee(attendee, index);
            eventUI.displayCancelSignupSuccess();
        } else {
            eventUI.displayCancelSignUpDouble();
        }
    }

    //others
}
