package SchedulingSystem;

import java.time.LocalTime;
import java.util.UUID;

/**
 * A class that manages the Events in a Schedule.
 */
public class EventManager {
    private Schedule mainSchedule = new Schedule();
    private EventSignUp eventSignUp = new EventSignUp();
    private EventScheduler eventScheduler = new EventScheduler();

    /**
     * Returns the Schedule of all the Events in the conference's main schedule.
     * @return the Schedule of all the Events in the conference's main schedule
     */
    public Schedule retrieveAllEvents() {
        return this.mainSchedule;
    }

    /**
     * Returns a new Schedule of the Events in the conference's main schedule that fall in a given time interval.
     *
     * All the Events in the new Schedule either:
     *  - have a start time at or before start, and an end time at or after start and at or before end, or
     *  - have a start time at or after start and before end, and an end time after end
     *
     * @param start the start time of the interval
     * @param end the end time of the interval
     * @return a new Schedule of Events that fall in a given time interval
     */
    public Schedule retrieveEventsByTimeInterval(LocalTime start, LocalTime end) {
        return mainSchedule.retrieveEventsByTimeInterval(start, end);
    }

    /**
     * Returns a new Schedule of the Events in the conference's main schedule that are hosted by the given speaker.
     *
     * @param speaker the speaker speaking at the Events
     * @return a new Schedule of Events that are hosted by the given speaker
     */
    public Schedule retrieveEventsBySpeaker(UUID speaker) {
        return mainSchedule.retrieveEventsBySpeaker(speaker);
    }

    /**
     * Returns a new Schedule of the Events in the conference's main schedule that have the given title.
     *
     * @param title the title of the Events
     * @return a new Schedule of Events that have the given title
     */
    public Schedule retrieveEventsByTitle(String title) {
        return mainSchedule.retrieveEventsByTitle(title);
    }

    /**
     * Returns a new Schedule of the Events in the conference's main schedule that the given attendee is attending.
     *
     * @param attendee the attendee
     * @return a new Schedule of Events that the given attendee is attending
     */
    public Schedule retrieveEventsByAttendee(UUID attendee) {
        return mainSchedule.retrieveEventsByAttendee(attendee);
    }

    /**
     * Returns a new Schedule of the Events in the conference's main schedule that the given attendee can sign up to.
     *
     * @param attendee the attendee
     * @return a new Schedule of Events that the given attendee can sign up to
     */
    public Schedule retrieveSignupAbleEvents(UUID attendee) {
        return mainSchedule.retrieveSignupAbleEvents(attendee);
    }

    /**
     * Sign up the given attendee for the Event at the given eventNumber.
     *
     * Note that this eventNumber is relative to the Schedule of all the events that the given
     * attendee can sign up to.
     *
     * @param attendee the attendee
     * @param eventNumber event number of the Event, relative to the Schedule of all the events that the given
     *                    attendee can sign up to
     * @throws IndexOutOfBoundsException if the eventNumber is greater than or equal the number of Events that the
     *                                   attendee can sign up to
     */
    public void registerAttendee(UUID attendee, int eventNumber) throws IndexOutOfBoundsException {
        eventSignUp.registerAttendee(mainSchedule, attendee, eventNumber);
    }

    /**
     * Remove the given attendee from the Event at the given eventNumber.
     *
     * Note that this eventNumber is relative to the Schedule of all the events that the given attendee
     * is signed up for.
     *
     * @param attendee the attendee
     * @param eventNumber event number of the Event, relative to the Schedule of all the events that the given attendee
     *                    is signed up for
     * @throws IndexOutOfBoundsException if the eventNumber is greater than or equal the number of Events that the
     *                                   attendee has signed up for
     */
    public void removeAttendee(UUID attendee, int eventNumber) throws IndexOutOfBoundsException {
        eventSignUp.removeAttendee(mainSchedule, attendee, eventNumber);
    }

    /**
     * Add a new Event to the conference's main schedule, with the given details.
     *
     * @param capacity the capacity
     * @param room the room
     * @param startTime the start time
     * @param title the title
     * @param speaker the speaker
     * @param duration the duration
     */
    public void scheduleEvent(int capacity, String room, LocalTime startTime, String title, UUID speaker, int duration) {
        eventScheduler.scheduleEvent(mainSchedule, capacity, room, startTime, title, speaker, duration);
    }

    /**
     * Remove the Event from the conference's main schedule at the given eventNumber.
     *
     * @param eventNumber the event number of the Event, relative to the main schedule
     */
    public void cancelEvent(int eventNumber) {
        eventScheduler.cancelEvent(mainSchedule, eventNumber);
    }

    /**
     * Change the start time of the Event from the conference's main schedule at the given eventNumber.
     *
     * @param eventNumber the event number of the Event, relative to the main schedule
     * @param newStartTime the new start time of the Event
     */
    public void rescheduleEvent(int eventNumber, LocalTime newStartTime) {
        eventScheduler.rescheduleEvent(mainSchedule, eventNumber, newStartTime);
    }
}
