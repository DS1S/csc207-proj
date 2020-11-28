package backend.systems.events.managers;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A class for filtering a list of Events by various criteria.
 */
class EventFilterer implements Serializable {
    /**
     * Returns a new list of the Events in a given list that overlap with a specified time interval.
     *
     * The end points of the interval are not included.
     *
     * @param events The original list of Events to be filtered.
     * @param start The start time of the interval.
     * @param end The end time of the interval.
     * @return A new list of Events that fall in the given time interval.
     */
    public List<Event> retrieveEventsByTimeInterval(List<Event> events, LocalTime start, LocalTime end) {
        List<Event> matchedEvents = new ArrayList<>();
        for (Event event: events) {
            if (!(event.getStartTime().compareTo(end) >= 0 || event.getEndTime().compareTo(start) <= 0)) {
                matchedEvents.add(event);
            }
        }
        return matchedEvents;
    }

    /**
     * Returns a new list of the Events in a given list that are hosted by the specified speaker.
     *
     * @param events The original list of Events to be filtered
     * @param speaker The UUID of the Speaker speaking at the Events.
     * @return A new list of Events that are hosted by the given Speaker.
     */
    public List<Event> retrieveEventsBySpeaker(List<Event> events, UUID speaker) {
        List<Event> matchedEvents = new ArrayList<>();
        for (Event event: events) {
            if (event.getSpeaker().equals(speaker)) {
                matchedEvents.add(event);
            }
        }
        return matchedEvents;
    }

    /**
     * Returns a new list of the Events in a given list that the specified Attendee is attending.
     *
     * @param events The original list of Events to be filtered.
     * @param attendee The UUID of the specified Attendee.
     * @return A new list of Events that the given Attendee is attending.
     */
    public List<Event> retrieveEventsByAttendee(List<Event> events, UUID attendee) {
        List<Event> matchedEvents = new ArrayList<>();
        for (Event event: events) {
            if (event.checkAttendee(attendee)) {
                matchedEvents.add(event);
            }
        }
        return matchedEvents;
    }

    /**
     * Returns a new list of the Events in a given list that the given attendee can sign up to.
     *
     * An attendee can sign up to an Event if they are not already signed up for the Event and the Event is
     * not at capacity.
     *
     * @param events The original list of Events to be filtered.
     * @param attendee The UUID of the Attendee.
     * @return A new list of Events that the given attendee can sign up to.
     */
    public List<Event> retrieveSignupAbleEvents(List<Event> events, UUID attendee) {
        List<Event> matchedEvents = new ArrayList<>();
        for (Event event: events) {
            if (!event.checkAttendee(attendee) && !event.atCapacity()) {
                matchedEvents.add(event);
            }
        }
        return matchedEvents;
    }


    /**
     * Returns a new list of the Events in a given list that have the specified title and speaker.
     *
     * @param events The original list of Events to be filtered.
     * @param speaker The UUID of the specified Speaker.
     * @param title The desired title of the event(s).
     * @return A list of events that have both speaker and title.
     */
    public List<Event> retrieveEventsBySpeakerAndTitle(List<Event> events, UUID speaker, String title) {
        List<Event> matchedEvents = new ArrayList<>();
        for (Event event: events) {
            if (event.getTitle().equals(title) && event.getSpeaker().equals(speaker)) {
                matchedEvents.add(event);
            }
        }
        return matchedEvents;
    }
}
