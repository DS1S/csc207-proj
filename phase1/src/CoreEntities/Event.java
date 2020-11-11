package CoreEntities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.util.UUID;

/**
 * An Event with capacity, attendees, room, start time, title, speaker
 * and duration.
 */
public class Event implements Serializable {

    /** The maximum capacity of this Event. */
    private int capacity;

    /** The list of attendees of this Event. */
    private List<UUID> attendees;

    /** The room in which this Event takes place. */
    private String room;

    /** The start time of this Event. */
    private LocalTime startTime;

    /** The title of this Event. */
    private String title;

    /** The speaker of this Event. */
    private UUID speaker;

    /** The duration of this Event. */
    private int duration;

    /**
     * Constructs a new Event with the given information.
     * @param capacity the capacity
     * @param room the room
     * @param startTime the start time
     * @param title the title
     * @param speaker the speaker
     * @param duration the duration
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
     * Sets this Event's capacity to capacity.
     * @param capacity the new capacity
     */
    public void setCapacity(int capacity) { this.capacity = capacity; }

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
     * Sets this Event's room to room.
     * @param room the new room
     */
    public void setRoom(String room) { this.room = room; }

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
     * Sets this Event's title to title.
     * @param title the new title
     */
    public void setTitle(String title) { this.title = title; }

    /**
     * Returns this Event's speaker.
     * @return the speaker
     */
    public UUID getSpeaker() { return this.speaker; }

    /**
     * Sets this Event's speaker to speaker.
     * @param speaker the new speaker
     */
    public void setSpeaker(UUID speaker) { this.speaker = speaker; }

    /**
     * Returns this Event's duration.
     * @return the duration
     */
    public int getDuration() { return this.duration; }

    /**
     * Sets this Event's duration to duration.
     * @param duration the new duration
     */
    public void setDuration(int duration) { this.duration = duration; }

    /**
     * Returns the string representation of the Event.
     * @return the string representation of the Event
     */
    @Override
    public String toString() {
        return '"' + title + "\" by " + speaker + "\n" +
                startTime + " to " + startTime.plusMinutes(duration) + " in " + room;
    }
}