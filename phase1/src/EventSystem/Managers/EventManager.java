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

    public EventManager() {
        mainSchedule = new ArrayList<>();
        eventFilterer = new EventFilterer();
        eventSignUp = new EventSignUp();
        eventScheduler = new EventScheduler();
    }

    /**
     * Returns the list of extracted data of all the Events in the conference's main schedule.
     * @return the list of extracted data of all the Events in the conference's main schedule
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
     * @param speaker the UUID of the speaker speaking at the Events
     * @return the list of extracted data of Events that are hosted by the given speaker
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
     * @param attendee the UUID of the attendee
     * @return the list of extracted data of Events that the given attendee is attending
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
     * @param attendee the UUID of the attendee
     * @return the list of extracted data of Events that the given attendee can sign up to
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
     * @param attendee the UUID of the attendee to be signed up
     * @param index the index of the Event, relative to the list of the events that the given attendee can sign up to
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
     * @param attendee the UUID of the attendee to be removed
     * @param index the index of the Event, relative to the list of the events that the given attendee is signed up for
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
     * @param capacity the capacity of the new Event
     * @param room the room of the new Event
     * @param startTime the start time of the new Event
     * @param title the title of the new Event
     * @param speaker the UUID of the speaker of the new Event
     * @param duration the duration of the new Event in minutes
     * @return the list of extracted data of Events that conflict with the scheduling of the new Event
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
     * @param index the index of the Event to be removed, relative to the main schedule
     */
    public void cancelEvent(int index) {
        eventScheduler.cancelEvent(mainSchedule, index);
    }

    /**
     * Returns the list of extracted data of Events from the conference's main schedule that conflict with
     * the rescheduling of the at the specified index.
     *
     * Reschedules the Event so that it has start time newStartTime and duration newDuration iff there are no
     * conflicting Events
     *
     * @param index the index of the Event to be reschedules
     * @param newStartTime the new start time for the Event
     * @param newDuration the new duration of the Event in minutes
     * @return the string representation of a list of Events that conflict with the rescheduling of the Event
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
     * Returns the list of all UUIDs of attendees attending any of a speakers talk.
     *
     * @param speakerUUID UUID of speaker
     * @return A list of attendee UUID for all talks the speaker hosts
     */
    public List<UUID> retrieveAttendees(UUID speakerUUID){
        List<Event> events = eventFilterer.retrieveEventsBySpeaker(mainSchedule, speakerUUID);
        return getUUIDSFromEvents(events);
    }

    /**
     * Returns the list of all UUIDs of attendees attending a specific event.
     *
     * @param speakerUUID UUID of speaker
     * @param title title of a talk
     * @return A list of attendee UUID for a specific event with param title and hosted by speaker.
     */
    public List<UUID> retrieveAttendees(String title, UUID speakerUUID){
        List<Event> events = eventFilterer.retrieveEventsBySpeakerAndTitle(mainSchedule, speakerUUID, title);
        return getUUIDSFromEvents(events);
    }

    private List<UUID> getUUIDSFromEvents(List<Event> events){
        List<UUID> attendeeIDS = new ArrayList<>();
        for (Event event : events){
            attendeeIDS.addAll(event.getAttendees());
        }
        return attendeeIDS;
    }
}
