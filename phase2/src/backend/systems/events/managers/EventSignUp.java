package backend.systems.events.managers;

import backend.coreentities.Event;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * A class for signing up attendees to Events in a schedule of events.
 */
class EventSignUp implements Serializable {
    private EventFilterer eventFilterer;

    /** Constructs a new EventSignUp */
    EventSignUp() { eventFilterer = new EventFilterer(); }

    /**
     * Register the given attendee for the Event at the specified index, relative to the events that the attendee
     * can sign up for.
     *
     * @param events The list of Events the attendee can sign up for.
     * @param attendee The UUID of the attendee to be registered.
     * @param index The index of the Event, relative to the events that the attendee can sign up for.
     * @throws IndexOutOfBoundsException if the given index is invalid.
     */
    public void registerAttendee(UUID attendee, List<Event> events, int index) throws IndexOutOfBoundsException {
        eventFilterer.retrieveSignupAbleEvents(events, attendee).get(index).addAttendee(attendee);
    }

    /**
     * Remove the given attendee for the Event at the specified index, relative to the events that the
     * attendee has signed up for.
     *
     * @param events The list of Events the attendee has signed up for.
     * @param attendee The UUID of the attendee to be removed.
     * @param index The index of the Event, relative to the events that the attendee has signed up for.
     * @throws IndexOutOfBoundsException if the given index is invalid.
     */
    public void removeAttendee(UUID attendee, List<Event> events, int index) throws IndexOutOfBoundsException {
        eventFilterer.retrieveEventsByAttendee(events, attendee).get(index).removeAttendee(attendee);
    }
}
