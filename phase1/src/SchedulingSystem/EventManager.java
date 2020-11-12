package SchedulingSystem;

import CoreEntities.Event;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A class that manages the Events in a schedule of events.
 */
public class EventManager {
    private List<Event> mainSchedule;
    private EventFilter eventFilter;
    private EventSignUpManager eventSignUpManager;
    private EventScheduler eventScheduler;

    EventManager() {
        eventFilter = new EventFilter();
        eventSignUpManager = new EventSignUpManager();
        eventScheduler = new EventScheduler();
    }

    /**
     * Returns the list of all the Events in the conference's main schedule.
     * @return the list of all the Events in the conference's main schedule
     */
    public List<Event> retrieveAllEvents() {
        return this.mainSchedule;
    }

    /**
     * Returns a new list of the Events in the conference's main schedule that fall in a given time interval.
     *
     * All the Events in the new list either:
     *  - have a start time at or before start, and an end time at or after start and at or before end, or
     *  - have a start time at or after start and before end, and an end time after end
     *
     * @param start the start time of the interval
     * @param end the end time of the interval
     * @return a new list of Events that fall in a given time interval
     */
    public List<Event> retrieveEventsByTimeInterval(LocalTime start, LocalTime end) {
        return eventFilter.retrieveEventsByTimeInterval(mainSchedule, start, end);
    }

    /**
     * Returns a new list of the Events in the conference's main schedule that are hosted by the given speaker.
     *
     * @param speaker the UUID of the speaker speaking at the Events
     * @return a new list of Events that are hosted by the given speaker
     */
    public List<Event> retrieveEventsBySpeaker(UUID speaker) {
        return eventFilter.retrieveEventsBySpeaker(mainSchedule, speaker);
    }

    /**
     * Returns a new list of the Events in the conference's main schedule that have the given title.
     *
     * @param title the title of the Events
     * @return a new list of Events that have the given title
     */
    public List<Event> retrieveEventsByTitle(String title) {
        return eventFilter.retrieveEventsByTitle(mainSchedule, title);
    }

    /**
     * Returns a new list of the Events in the conference's main schedule that the given attendee is attending.
     *
     * @param attendee the UUID of the attendee
     * @return a new list of Events that the given attendee is attending
     */
    public List<Event> retrieveEventsByAttendee(UUID attendee) {
        return eventFilter.retrieveEventsByAttendee(mainSchedule, attendee);
    }

    /**
     * Returns a new list of the Events in the conference's main schedule that the given attendee can sign up to.
     *
     * @param attendee the UUID of the attendee
     * @return a new list of Events that the given attendee can sign up to
     */
    public List<Event> retrieveSignupAbleEvents(UUID attendee) {
        return eventFilter.retrieveSignupAbleEvents(mainSchedule, attendee);
    }

    /**
     * Sign up the given attendee for the Event at the given index.
     *
     * Note that this index is relative to the list of the events in the conference's main schedule that
     * the given attendee can sign up to.
     *
     * @param attendee the UUID of the attendee to be signed up
     * @param index the index of the Event, relative to the list of the events that the given attendee can sign up to
     * @throws IndexOutOfBoundsException if the index is negative, or greater than or equal the number of Events that
     *                                   the attendee can sign up to
     */
    public void registerAttendee(UUID attendee, int index) throws IndexOutOfBoundsException {
        eventSignUpManager.registerAttendee(attendee, mainSchedule, index);
    }

    /**
     * Remove the given attendee from the Event at the given index.
     *
     * Note that this index is relative to the list of the events in the conference's main schedule that
     * the given attendee is signed up for.
     *
     * @param attendee the UUID of the attendee to be removed
     * @param index the index of the Event, relative to the list of the events that the given attendee is signed up for
     * @throws IndexOutOfBoundsException if the index is negative, or greater than or equal the number of Events that
     *                                   the attendee has signed up for
     */
    public void removeAttendee(UUID attendee, int index) throws IndexOutOfBoundsException {
        eventSignUpManager.removeAttendee(attendee, mainSchedule, index);
    }

    /**
     * Returns the list of Events from the conference's main schedule that conflict with the scheduling of a new Event
     * with the given details.
     *
     * Adds the new Event to the conference's main schedule iff there are no conflicting Events.
     *
     * @param capacity the capacity of the new Event
     * @param room the room of the new Event
     * @param startTime the start time of the new Event
     * @param title the title of the new Event
     * @param speaker the UUID of the speaker of the new Event
     * @param duration the duration of the new Event in minutes
     * @return a list of Events that conflict with the scheduling of the new Event
     */
    public List<Event> scheduleEvent(int capacity, String room, LocalTime startTime, String title, UUID speaker,
                                     int duration) {
        return eventScheduler.scheduleEvent(mainSchedule, capacity, room, startTime, title, speaker, duration);
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
     * Returns the list of Events from the conference's main schedule that conflict with the rescheduling of the
     * at the specified index.
     *
     * Reschedules the Event so that it has start time newStartTime and duration newDuration iff there are no
     * conflicting Events
     *
     * @param index the index of the Event to be reschedules
     * @param newStartTime the new start time for the Event
     * @param newDuration the new duration of the Event in minutes
     * @return a list of Events that conflict with the rescheduling of the Event
     * @throws IndexOutOfBoundsException if the given index is invalid
     */
    public List<Event> rescheduleEvent(int index, LocalTime newStartTime,
                                       int newDuration) throws IndexOutOfBoundsException {
        return eventScheduler.rescheduleEvent(mainSchedule, index, newStartTime, newDuration);
    }
}
