package EventSystem.Managers;

import CoreEntities.Event;
import EventSystem.EventSystem;
import EventSystem.Managers.EventFilterer;

import java.util.List;
import java.util.UUID;

/**
 * A class for signing up attendees to Events in a schedule of events.
 */
class EventSignUp {

    /** An EventFilterer for filtering a list of Events by various criteria. */
    private EventFilterer eventFilterer;

    /** Constructs a new EventSignUp */
    EventSignUp() { eventFilterer = new EventFilterer(); }

    /**
     * Register the given attendee for the Event at the specified index, relative to the events that the attendee
     * can sign up for.
     *
     * @param events the list of Events
     * @param attendee the UUID of the attendee to be registered
     * @param index the index of the Event, relative to the events that the attendee can sign up for
     * @throws IndexOutOfBoundsException if the given index is invalid
     */
    public void registerAttendee(UUID attendee, List<Event> events, int index) throws IndexOutOfBoundsException {
        eventFilterer.retrieveSignupAbleEvents(events, attendee).get(index).addAttendee(attendee);
    }

    /**
     * Remove the given attendee for the Event at the specified index, relative to the events that the
     * attendee has signed up for.
     *
     * @param events the list of Events
     * @param attendee the UUID of the attendee to be removed
     * @param index the index of the Event, relative to the events that the attendee has signed up for
     * @throws IndexOutOfBoundsException if the given index is invalid
     */
    public void removeAttendee(UUID attendee, List<Event> events, int index) throws IndexOutOfBoundsException {
        eventFilterer.retrieveEventsByAttendee(events, attendee).get(index).removeAttendee(attendee);
    }
}
