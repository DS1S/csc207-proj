package backend.systems.events.managers;

import backend.entities.Event;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A class for adding new Events to a schedule of events.
 */
class EventScheduler implements Serializable {
    private final EventFilterer eventFilter;

    /** Constructs a new EventScheduler. */
    EventScheduler() { eventFilter = new EventFilterer(); }

    private List<Event> getRoomConflicts(List<Event> events, String room, LocalTime start, LocalTime end) {
        List<Event> conflictingEvents = new ArrayList<>();
        List<Event> filteredEvents = eventFilter.retrieveEventsByTimeInterval(events, start, end);
        for (Event event: filteredEvents) {
            if (event.getRoom().equals(room)) {
                conflictingEvents.add(event);
            }
        }
        return conflictingEvents;
    }

    private List<Event> getSpeakerConflicts(List<Event> events, UUID speaker, LocalTime start, LocalTime end) {
        List<Event> conflictingEvents = new ArrayList<>();
        List<Event> filteredEvents = eventFilter.retrieveEventsByTimeInterval(events, start, end);
        for (Event event: filteredEvents) {
            if (event.isSpeaker(speaker)) {
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
     * @param speakers a list of UUIDs of the speakers for the new Event
     * @param duration the duration of the new Event in minutes
     * @return a list of Events that conflict with the scheduling of the new Event
     */
    public List<Event> scheduleEvent(List<Event> events, int capacity, String room, LocalTime startTime, String title, List<UUID> speakers, int duration) {
        List<Event> conflictingEvents = new ArrayList<>(getRoomConflicts(events, room, startTime, startTime.plusMinutes(duration - 1)));
        for (UUID speaker: speakers) {
            conflictingEvents.addAll(getSpeakerConflicts(events, speaker, startTime, startTime.plusMinutes(duration - 1)));
        }
        removeDuplicateConflictedEvents(conflictingEvents);

        if (conflictingEvents.isEmpty()) {
            events.add(new Event(capacity, room, startTime, title, speakers, duration));
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

        List<Event> conflictingEvents = new ArrayList<>(getRoomConflicts(events, event.getRoom(), newStartTime, newStartTime.plusMinutes(newDuration)));
        for (UUID speaker: event.getSpeakers())
            conflictingEvents.addAll(getSpeakerConflicts(events, speaker, newStartTime, newStartTime.plusMinutes(newDuration)));
        removeDuplicateConflictedEvents(conflictingEvents);

        if (conflictingEvents.isEmpty()) {
            event.setStartTime(newStartTime);
            event.setDuration(newDuration);
        }
        else if(conflictingEvents.size() == 1) {
            if (conflictingEvents.get(0).equals(event)) {
                event.setStartTime(newStartTime);
                event.setDuration(newDuration);
                conflictingEvents.remove(0);
            }
        }

        return conflictingEvents;
    }

    private void removeDuplicateConflictedEvents(List<Event> events) {
        List<Integer> dupPositions = new ArrayList<>();
        for(int i = 0; i < events.size() - 1; i++) {
            for(int j = i + 1; j < events.size(); j++) {
                if (events.get(i).equals(events.get(j))) dupPositions.add(j);
            }
        }

        for (int dupPos: dupPositions) {
            events.remove(dupPos);
        }
    }
}
