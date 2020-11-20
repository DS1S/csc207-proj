package EventSystem.Managers;

import CoreEntities.Event;
import EventSystem.EventSystem;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.*;

/**
 * A class that manages the Events in a schedule of events.
 */
public class EventManager implements Serializable {
    private List<Event> mainSchedule;
    private EventFilterer eventFilterer;
    private EventSignUp eventSignUp;
    private EventScheduler eventScheduler;

    /**
     * Constructs an EventManager, with empty schedule and default event components.
     */
    public EventManager() {
        mainSchedule = new ArrayList<>();
        eventFilterer = new EventFilterer();
        eventSignUp = new EventSignUp();
        eventScheduler = new EventScheduler();
    }

    /**
     * Returns the list of extracted data of all the Events in the conference's main schedule.
     * @return The list of extracted data of all the Events in the conference's main schedule.
     */
    public List<Map<String, Object>> retrieveAllEvents() {
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (Event event: mainSchedule) {
            dataList.add(event.extractData());
        }
        return dataList;
    }

    /**
     * Returns the list of extracted data of the Events in the conference's main schedule that are
     * hosted by the given speaker.
     *
     * @param speaker The UUID of the speaker speaking at the Events.
     * @return The list of extracted data of Events that are hosted by the given speaker.
     */
    public List<Map<String, Object>> retrieveEventsBySpeaker(UUID speaker) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (Event event: eventFilterer.retrieveEventsBySpeaker(mainSchedule, speaker)) {
            dataList.add(event.extractData());
        }
        return dataList;
    }

    /**
     * Returns the list of extracted data of the Events in the conference's main schedule that the given
     * attendee is attending.
     *
     * @param attendee The UUID of the specified Attendee.
     * @return The list of extracted data of Events that the given Attendee is attending.
     */
    public List<Map<String, Object>> retrieveEventsByAttendee(UUID attendee) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (Event event: eventFilterer.retrieveEventsByAttendee(mainSchedule, attendee)) {
            dataList.add(event.extractData());
        }
        return dataList;
    }

    /**
     * Returns the list of extracted data of the Events in the conference's main schedule that the given
     * attendee can sign up to.
     *
     * @param attendee The UUID of the specified Attendee.
     * @return The list of extracted data of Events that the given Attendee can sign up for.
     */
    public List<Map<String, Object>> retrieveSignupAbleEvents(UUID attendee) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (Event event: eventFilterer.retrieveSignupAbleEvents(mainSchedule, attendee)) {
            dataList.add(event.extractData());
        }
        return dataList;
    }

    /**
     * Sign up the given attendee for the Event at the given index.
     *
     * Note that this index is relative to the list of the events in the conference's main schedule that
     * the given attendee can sign up to.
     *
     * @param attendee The UUID of the specified Attendee to be signed up.
     * @param index The index of the Event, relative to the list of the events that the given Attendee can sign up for.
     */
    public void registerAttendee(UUID attendee, int index) {
        eventSignUp.registerAttendee(attendee, mainSchedule, index);
    }

    /**
     * Remove the given attendee from the Event at the given index.
     *
     * Note that this index is relative to the list of the events in the conference's main schedule that
     * the given attendee is signed up for.
     *
     * @param attendee The UUID of the Attendee to be removed.
     * @param index The index of the Event, relative to the list of the events that the given Attendee is signed up for.
     */
    public void removeAttendee(UUID attendee, int index) {
        eventSignUp.removeAttendee(attendee, mainSchedule, index);
    }

    /**
     * Returns the list of extracted data of Events from the conference's main schedule that conflict with
     * the scheduling of a new Event with the given details.
     *
     * Adds the new Event to the conference's main schedule iff there are no conflicting Events.
     *
     * @param capacity The capacity of the new Event.
     * @param room The room in which the new Event is taking place.
     * @param startTime The start time of the new Event.
     * @param title The title of the new Event.
     * @param speaker The UUID of the Speaker of the new Event.
     * @param duration The duration of the new Event, in minutes.
     * @return The list of extracted data of Events that conflict with the scheduling of the new Event.
     */
    public List<Map<String, Object>> scheduleEvent(int capacity, String room, LocalTime startTime, String title, UUID speaker,
                                     int duration) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<Event> response = eventScheduler.scheduleEvent(mainSchedule, capacity, room, startTime, title, speaker,
                                duration);
        for (Event event: response) {
            dataList.add(event.extractData());
        }
        return dataList;
    }

    /**
     * Removes the Event at the given index from the conference's main schedule.
     *
     * @param index the index of the Event to be removed, relative to the main schedule.
     */
    public void cancelEvent(int index) {
        eventScheduler.cancelEvent(mainSchedule, index);
    }

    /**
     * Returns the list of extracted data of Events from the conference's main schedule that conflict with
     * the rescheduling of the at the specified index.
     *
     * Reschedules the Event so that it has start time newStartTime and duration newDuration iff there are no
     * conflicting Events.
     *
     * @param index The index of the Event to be rescheduled.
     * @param newStartTime The new start time for this Event.
     * @param newDuration The new duration of this Event, in minutes.
     * @return The string representation of a list of Events that conflict with the rescheduling of the Event.
     */
    public List<Map<String, Object>> rescheduleEvent(int index, LocalTime newStartTime,
                                       int newDuration) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (Event event: eventScheduler.rescheduleEvent(mainSchedule, index, newStartTime, newDuration)) {
            dataList.add(event.extractData());
        }
        return dataList;
    }

    /**
     * Returns the list of all UUIDs of attendees attending a specific event.
     *
     * @param speakerUUID The UUID of the specified Speaker.
     * @param title title of a talk
     * @return A list of attendee UUIDs for a specific event with param title and hosted by the specified Speaker.
     */
    public List<UUID> retrieveAttendees(String title, UUID speakerUUID) {
        List<Event> events = eventFilterer.retrieveEventsBySpeakerAndTitle(mainSchedule, speakerUUID, title);
        return getUUIDSFromEvents(events);
    }

    private List<UUID> getUUIDSFromEvents(List<Event> events) {
        List<UUID> attendeeIDS = new ArrayList<>();
        for (Event event : events) {
            attendeeIDS.addAll(event.getAttendees());
        }
        return attendeeIDS;
    }
}
