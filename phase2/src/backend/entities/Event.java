package backend.entities;

import java.io.Serializable;
import java.util.*;
import java.time.LocalTime;

/**
 * An Event with capacity, attendees, room, start time, title, speaker
 * and duration.
 */
public class Event implements Serializable {
    private final int capacity;
    private final List<UUID> attendees;
    private final String room;
    private LocalTime startTime;
    private final String title;
    private final List<UUID> speakers;
    private int duration;

    /**
     * Constructs a new Event with a capacity, room, start time, its title, its speaker, and duration.
     * @param capacity The number of people this event can have.
     * @param room The room in which the event is taking place.
     * @param startTime The starting time of the event.
     * @param title The event's title.
     * @param speakers A list of UUIDs of the speakers speaking at this event.
     * @param duration The duration of the event, in minutes.
     */
    public Event(int capacity, String room, LocalTime startTime, String title, List<UUID> speakers, int duration) {
        this.capacity = capacity;
        this.attendees = new ArrayList<>();
        this.room = room;
        this.startTime = startTime;
        this.title = title;
        this.speakers = speakers;
        this.duration = duration;
    }

    /**
     * Returns true iff the Event is at capacity.
     * @return Whether the Event is at capacity.
     */
    public boolean atCapacity() { return this.attendees.size() == capacity; }

    /**
     * Gets this Event's list of Attendees.
     * @return The attendees of the event.
     */
    public List<UUID> getAttendees() { return this.attendees; }

    /**
     * Add an attendee to the Event's list of Attendees.
     * @param attendee The attendee to be added.
     */
    public void addAttendee(UUID attendee) { this.attendees.add(attendee); }

    /**
     * Remove an attendee from the Event's list of Attendees.
     * @param attendee The attendee to be removed.
     */
    public void removeAttendee(UUID attendee) { this.attendees.remove(attendee); }

    /**
     * Remove all attendees from the Event's list of Attendees
     */
    public void removeAllAttendees() { this.attendees.clear(); }

    /**
     * Returns true iff the attendee is attending the Event.
     * @return Whether the attendee is attending the Event.
     */
    public boolean checkAttendee(UUID attendee) { return this.attendees.contains(attendee); }

    /**
     * Gets this Event's room.
     * @return This Event's room.
     */
    public String getRoom() { return this.room; }

    /**
     * Gets this Event's start time.
     * @return The start time of the Event.
     */
    public LocalTime getStartTime() { return this.startTime; }

    /**
     * Sets this Event's start time to time.
     * @param time The new start time.
     */
    public void setStartTime(LocalTime time) { this.startTime = time; }

    /**
     * Gets this Event's end time.
     * @return The ending time of the event.
     */
    public LocalTime getEndTime() { return startTime.plusMinutes(duration); }

    /**
     * Gets this Event's title.
     * @return The title of the Event.
     */
    public String getTitle() { return this.title; }

    /**
     * Check whether the user with the given UUID is a speaker for this Event.
     * @param uuid the UUID to be checked
     * @return The speaker of this Event.
     */
    public boolean isSpeaker(UUID uuid) { return this.speakers.contains(uuid); }

    /**
     * Gets this Event's list of Speakers.
     * @return The speakers of the event.
     */
    public List<UUID> getSpeakers() { return this.speakers; }

    /**
     * Sets this Event's duration to duration.
     * @param duration The new duration of this Event.
     */
    public void setDuration(int duration) { this.duration = duration; }

    /**
     * Returns the details about this Event as a map.
     *
     * The map maps the name of each attribute to the attribute itself.
     * @return The details about this Event as a map.
     */
    public Map<String, Object> extractData() {
        Map<String, Object> data = new HashMap<>();

        data.put("Title", title);
        data.put("Speaker", speakers);
        data.put("StartTime", startTime);
        data.put("EndTime", getEndTime());
        data.put("Room", room);
        data.put("Registered", attendees.size());
        data.put("Capacity", capacity);

        return data;
    }
}