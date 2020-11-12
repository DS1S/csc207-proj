package SchedulingSystem;

import CoreEntities.Event;
import SchedulingSystem.EventUtil.EventFilterer;
import SchedulingSystem.EventUtil.EventScheduler;
import SchedulingSystem.EventUtil.EventSignUp;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

/**
 * A class that manages the Events in a schedule of events.
 */
public class EventManager {
    private List<Event> mainSchedule;
    private EventFilterer eventFilterer;
    private EventSignUp eventSignUp;
    private EventScheduler eventScheduler;

    EventManager() {
        eventFilterer = new EventFilterer();
        eventSignUp = new EventSignUp();
        eventScheduler = new EventScheduler();
    }

    private String eventsToString(List<Event> events) {
        StringBuilder eventsString = new StringBuilder();
        int i = 1;
        for (Event event : events) {
            eventsString.append("Event #").append(i).append(": ").append(event.toString());
            i += 1;
        }
        return eventsString.toString();
    }

    /**
     * Returns the string representation of the list of all the Events in the conference's main schedule.
     * @return the string representation of all the Events in the conference's main schedule
     */
    public String retrieveAllEvents() {
        return eventsToString(this.mainSchedule);
    }

    /**
     * Returns the string representation of a new list of the Events in the conference's main schedule that are
     * hosted by the given speaker.
     *
     * @param speaker the UUID of the speaker speaking at the Events
     * @return the string representation of a new list of Events that are hosted by the given speaker
     */
    public String retrieveEventsBySpeaker(UUID speaker) {
        return eventsToString(eventFilterer.retrieveEventsBySpeaker(mainSchedule, speaker));
    }

    /**
     * Returns the string representation of a new list of the Events in the conference's main schedule that have the
     * given title.
     *
     * @param title the title of the Events
     * @return the string representation of a new list of Events that have the given title
     */
    public String retrieveEventsByTitle(String title) {
        return eventsToString(eventFilterer.retrieveEventsByTitle(mainSchedule, title));
    }

    /**
     * Returns the string representation of a new list of the Events in the conference's main schedule that the given
     * attendee is attending.
     *
     * @param attendee the UUID of the attendee
     * @return the string representation of a new list of Events that the given attendee is attending
     */
    public String retrieveEventsByAttendee(UUID attendee) {
        return eventsToString(eventFilterer.retrieveEventsByAttendee(mainSchedule, attendee));
    }

    /**
     * Returns the string representation of a new list of the Events in the conference's main schedule that the given
     * attendee can sign up to.
     *
     * @param attendee the UUID of the attendee
     * @return the string representation of a new list of Events that the given attendee can sign up to
     */
    public String retrieveSignupAbleEvents(UUID attendee) {
        return eventsToString(eventFilterer.retrieveSignupAbleEvents(mainSchedule, attendee));
    }

    /**
     * Sign up the given attendee for the Event at the given index.
     *
     * Note that this index is relative to the list of the events in the conference's main schedule that
     * the given attendee can sign up to.
     *
     * @param attendee the UUID of the attendee to be signed up
     * @param index the index of the Event, relative to the list of the events that the given attendee can sign up to
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public void registerAttendee(UUID attendee, int index) throws IndexOutOfBoundsException {
        eventSignUp.registerAttendee(attendee, mainSchedule, index);
    }

    /**
     * Remove the given attendee from the Event at the given index.
     *
     * Note that this index is relative to the list of the events in the conference's main schedule that
     * the given attendee is signed up for.
     *
     * @param attendee the UUID of the attendee to be removed
     * @param index the index of the Event, relative to the list of the events that the given attendee is signed up for
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public void removeAttendee(UUID attendee, int index) throws IndexOutOfBoundsException {
        eventSignUp.removeAttendee(attendee, mainSchedule, index);
    }

    /**
     * Returns the string representation of the list of Events from the conference's main schedule that conflict with
     * the scheduling of a new Event with the given details.
     *
     * Adds the new Event to the conference's main schedule iff there are no conflicting Events.
     *
     * @param capacity the capacity of the new Event
     * @param room the room of the new Event
     * @param startTime the start time of the new Event
     * @param title the title of the new Event
     * @param speaker the UUID of the speaker of the new Event
     * @param duration the duration of the new Event in minutes
     * @return the string representation of a list of Events that conflict with the scheduling of the new Event
     */
    public String scheduleEvent(int capacity, String room, LocalTime startTime, String title, UUID speaker,
                                     int duration) {
        return eventsToString(eventScheduler.scheduleEvent(mainSchedule, capacity, room, startTime, title, speaker,
                duration));
    }

    /**
     * Removes the Event at the given index from the conference's main schedule.
     *
     * @param index the index of the Event to be removed, relative to the main schedule
     * @throws IndexOutOfBoundsException if the given index is invalid
     */
    public void cancelEvent(int index) throws IndexOutOfBoundsException {
        eventScheduler.cancelEvent(mainSchedule, index);
    }

    /**
     * Returns the string representation of the list of Events from the conference's main schedule that conflict with
     * the rescheduling of the at the specified index.
     *
     * Reschedules the Event so that it has start time newStartTime and duration newDuration iff there are no
     * conflicting Events
     *
     * @param index the index of the Event to be reschedules
     * @param newStartTime the new start time for the Event
     * @param newDuration the new duration of the Event in minutes
     * @return the string representation of a list of Events that conflict with the rescheduling of the Event
     * @throws IndexOutOfBoundsException if the given index is invalid
     */
    public String rescheduleEvent(int index, LocalTime newStartTime,
                                       int newDuration) throws IndexOutOfBoundsException {
        return eventsToString(eventScheduler.rescheduleEvent(mainSchedule, index, newStartTime, newDuration));
    }
}
