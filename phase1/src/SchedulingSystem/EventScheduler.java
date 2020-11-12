package SchedulingSystem;

import CoreEntities.Event;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A class for adding new Events to a schedule of events.
 */
class EventScheduler {

    /** An EventFilter for filtering a list of Events by various criteria. */
    private final EventFilterer eventFilter;

    /** Constructs a new EventScheduler. */
    EventScheduler() { eventFilter = new EventFilterer(); }

    /**
     * Returns a new list of the Events in a given list that are in the specified room during the specified interval.
     *
     * @param events the original list of Events
     * @param room the room
     * @param start the start time of the interval
     * @param end the end time of the interval
     * @return a new list of Events that are in the specified room during the specified interval
     */
    private List<Event> getRoomConflicts(List<Event> events, String room, LocalTime start, LocalTime end) {
        List<Event> conflictingEvents = new ArrayList<>();
        for (Event event: eventFilter.retrieveEventsByTimeInterval(events, start, end)) {
            if (event.getRoom().equals(room)) {
                conflictingEvents.add(event);
            }
        }
        return conflictingEvents;
    }

    /**
     * Returns a new list of the Events in a given list that are hosted by the specified speaker during the specified
     * interval.
     *
     * @param events the original list of Events
     * @param speaker the UUID of the speaker
     * @param start the start time of the interval
     * @param end the end time of the interval
     * @return a new list of Events that are in the specified room during the specified interval
     */
    private List<Event> getSpeakerConflicts(List<Event> events, UUID speaker, LocalTime start, LocalTime end) {
        List<Event> conflictingEvents = new ArrayList<>();
        for (Event event: eventFilter.retrieveEventsByTimeInterval(events, start, end)) {
            if (event.getSpeaker().equals(speaker)) {
                conflictingEvents.add(event);
            }
        }
        return conflictingEvents;
    }

    /**
     * Returns the list of Events that conflict with the scheduling of a new Event with the given details.
     *
     * Adds the new Event to the given list of Events iff there are no conflicting Events.
     *
     * @param events the list of Events to be added to
     * @param capacity the capacity of the new Event
     * @param room the room of the new Event
     * @param startTime the start time of the new Event
     * @param title the title of the new Event
     * @param speaker the UUID of the speaker of the new Event
     * @param duration the duration of the new Event in minutes
     * @return a list of Events that conflict with the scheduling of the new Event
     */
    public List<Event> scheduleEvent(List<Event> events, int capacity, String room, LocalTime startTime, String title, UUID speaker, int duration) {
        List<Event> conflictingEvents = new ArrayList<>();
        conflictingEvents.addAll(getRoomConflicts(events, room, startTime, startTime.plusMinutes(duration)));
        conflictingEvents.addAll(getSpeakerConflicts(events, speaker, startTime, startTime.plusMinutes(duration)));

        if (conflictingEvents.isEmpty()) {
            events.add(new Event(capacity, room, startTime, title, speaker, duration));
        }

        return conflictingEvents;
    }

    /**
     * Removes the Event from a given list of Events that is at the specified index.
     *
     * @param events the list of Events
     * @param index the index of the Event to be removed
     * @throws IndexOutOfBoundsException if the given index is invalid
     */
    public void cancelEvent(List<Event> events, int index) throws IndexOutOfBoundsException {
        events.remove(index);
    }

    /**
     * Returns the list of Events that conflict with the rescheduling of the Event from a given list of Events
     * that is at the specified index.
     *
     * Reschedules the Event so that it has start time newStartTime and duration newDuration iff there are no
     * conflicting Events
     *
     * @param events the list of Events
     * @param index the index of the Event to be reschedules
     * @param newStartTime the new start time for the Event
     * @param newDuration the new duration of the Event in minutes
     * @return a list of Events that conflict with the rescheduling of the Event
     * @throws IndexOutOfBoundsException if the given index is invalid
     */
    public List<Event> rescheduleEvent(List<Event> events, int index, LocalTime newStartTime,
                                       int newDuration) throws IndexOutOfBoundsException {
        Event event = events.get(index);

        List<Event> conflictingEvents = new ArrayList<>();
        conflictingEvents.addAll(getRoomConflicts(events, event.getRoom(), newStartTime, newStartTime.plusMinutes(newDuration)));
        conflictingEvents.addAll(getSpeakerConflicts(events, event.getSpeaker(), newStartTime, newStartTime.plusMinutes(newDuration)));

        if (conflictingEvents.isEmpty()) {
            event.setStartTime(newStartTime);
            event.setDuration(newDuration);
        }

        return conflictingEvents;
    }
}
