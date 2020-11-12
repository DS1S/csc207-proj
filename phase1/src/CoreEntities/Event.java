package CoreEntities;

import java.io.Serializable;
import java.util.*;
import java.time.LocalTime;

/**
 * An Event with capacity, attendees, room, start time, title, speaker
 * and duration.
 */
public class Event implements Serializable {

    /** The maximum capacity of this Event. */
    private int capacity;

    /** The list of UUIDs of the attendees of this Event. */
    private List<UUID> attendees;

    /** The room in which this Event takes place. */
    private String room;

    /** The start time of this Event. */
    private LocalTime startTime;

    /** The title of this Event. */
    private String title;

    /** The UUID of the speaker of this Event. */
    private UUID speaker;

    /** The duration of this Event in minutes. */
    private int duration;

    /**
     * Constructs a new Event with the given information.
     * @param capacity the capacity
     * @param room the room
     * @param startTime the start time
     * @param title the title
     * @param speaker the UUID of the speaker
     * @param duration the duration in minutes
     */
    public Event(int capacity, String room, LocalTime startTime, String title, UUID speaker, int duration) {
        this.capacity = capacity;
        this.attendees = new ArrayList<>();
        this.room = room;
        this.startTime = startTime;
        this.title = title;
        this.speaker = speaker;
        this.duration = duration;
    }

    /**
     * Returns this Event's capacity.
     * @return the capacity
     */
    public int getCapacity() { return this.capacity; }

    /**
     * Returns true iff the Event is at capacity.
     * @return whether the Event is at capacity
     */
    public boolean atCapacity() { return this.attendees.size() == capacity; }

    /**
     * Returns the Event's list of attendees.
     * @return the attendees of the event
     */
    public List<UUID> getAttendees() { return this.attendees; }

    /**
     * Add an attendee to the Event's list of attendee.
     * @param attendee the attendee to be added
     */
    public void addAttendee(UUID attendee) { this.attendees.add(attendee); }

    /**
     * Remove an attendee from the Event's list of attendee.
     * @param attendee the attendee to be removed
     */
    public void removeAttendee(UUID attendee) { this.attendees.remove(attendee); }

    /**
     * Returns true iff the attendee is attending the Event.
     * @return whether the attendee is attending the Event
     */
    public boolean checkAttendee(UUID attendee) { return this.attendees.contains(attendee); }

    /**
     * Returns this Event's room.
     * @return the room
     */
    public String getRoom() { return this.room; }

    /**
     * Returns this Event's start time.
     * @return the start time
     */
    public LocalTime getStartTime() { return this.startTime; }

    /**
     * Sets this Event's start time to time.
     * @param time the new start time
     */
    public void setStartTime(LocalTime time) { this.startTime = time; }

    /**
     * Returns this Event's end time.
     * @return the end time
     */
    public LocalTime getEndTime() { return startTime.plusMinutes(duration); }

    /**
     * Returns this Event's title.
     * @return the title
     */
    public String getTitle() { return this.title; }

    /**
     * Returns the UUID of this Event's speaker.
     * @return the speaker
     */
    public UUID getSpeaker() { return this.speaker; }

    /**
     * Returns this Event's duration in minutes.
     * @return the duration
     */
    public int getDuration() { return this.duration; }

    /**
     * Sets this Event's duration to duration.
     * @param duration the new duration
     */
    public void setDuration(int duration) { this.duration = duration; }

    public Map<String, Object> extractData() {
        Map<String, Object> data = new HashMap<>();

        data.put("Title", title);
        data.put("Speaker", speaker);
        data.put("StartTime", startTime);
        data.put("EndTime", getEndTime());
        data.put("Room", room);
        data.put("Registered", attendees.size());
        data.put("Capacity", capacity);

        return data;
    }
}