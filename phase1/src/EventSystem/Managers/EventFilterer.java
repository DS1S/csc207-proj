package EventSystem.Managers;

import CoreEntities.Event;
import EventSystem.EventSystem;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A class for filtering a list of Events by various criteria.
 */
class EventFilterer {
    /**
     * Returns a new list of the Events in a given list that fall in a specified time interval.
     *
     * All the Events in the new list either:
     *  - have a start time at or before start, and an end time at or after start and at or before end, or
     *  - have a start time at or after start and before end, and an end time after end
     *
     * @param events the original list of Events to be filtered
     * @param start the start time of the interval
     * @param end the end time of the interval
     * @return a new list of Events that fall in the given time interval
     */
    public List<Event> retrieveEventsByTimeInterval(List<Event> events, LocalTime start, LocalTime end) {
        List<Event> matchedEvents = new ArrayList<>();
        for (Event event: events) {
            if (event.getStartTime().compareTo(start) <= 0 && event.getEndTime().compareTo(start) >= 0
                    && event.getEndTime().compareTo(end) <= 0) {
                matchedEvents.add(event);
            }
            else if (event.getStartTime().compareTo(start) >= 0 && event.getStartTime().compareTo(end) < 0
                    && event.getEndTime().compareTo(end) > 0) {
                matchedEvents.add(event);
            }
        }
        return matchedEvents;
    }

    /**
     * Returns a new list of the Events in a given list that are hosted by the specified speaker.
     *
     * @param events the original list of Events to be filtered
     * @param speaker the UUID speaker speaking at the Events
     * @return a new list of Events that are hosted by the given speaker
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
     * Returns a new list of the Events in a given list that the specified attendee is attending.
     *
     * @param events the original list of Events to be filtered
     * @param attendee the UUID attendee
     * @return a new list of Events that the given attendee is attending
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
     * @param events the original list of Events to be filtered
     * @param attendee the UUID of the attendee
     * @return a new list of Events that the given attendee can sign up to
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
     * @param events the original list of Events to be filtered
     * @param speaker the UUID of the speaker
     * @param title the title of the Events
     * @return List of events that match criteria of Speaker and title.
     */
    public List<Event> retrieveEventsBySpeakerAndTitle(List<Event> events, UUID speaker, String title){
        List<Event> matchedEvents = new ArrayList<>();
        for (Event event: events) {
            if (event.getTitle().equals(title) && event.getSpeaker().equals(speaker)) {
                matchedEvents.add(event);
            }
        }
        return matchedEvents;
    }
}
