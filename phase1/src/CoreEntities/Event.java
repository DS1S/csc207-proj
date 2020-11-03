package CoreEntities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.util.UUID;

public class Event implements Serializable {
    private int capacity;
    private List<UUID> attendees;
    private String room;
    private LocalTime startTime;
    private String title;
    private UUID speaker;
    private int duration;

    public Event(int capacity, String room, LocalTime startTime, String title, UUID speaker, int duration) {
        this.capacity = 2;
        this.attendees = new ArrayList<>();
        this.room = room;
        this.startTime = startTime;
        this.title = title;
        this.speaker = speaker;
        this.duration = 60;
    }

    public int getCapacity() { return this.capacity; }

    public void setCapacity(int capacity) { this.capacity = capacity; }
    
    public boolean atCapacity() { return this.attendees.size() == capacity; }

    public List<UUID> getAttendees() { return this.attendees; }

    public void addAttendee(UUID attendee) { this.attendees.add(attendee); }

    public void removeAttendee(UUID attendee) { this.attendees.remove(attendee); }
    
    public boolean checkAttendee(UUID attendee) { return this.attendees.contains(attendee); }

    public String getRoom() { return this.room; }

    public void setRoom(String room) { this.room = room; }

    public LocalTime getStartTime() { return this.startTime; }

    public void setStartTime(LocalTime time) { this.startTime = time; }

    public LocalTime getEndTime() { return startTime.plusMinutes(duration); }

    public String getTitle() { return this.title; }

    public void setTitle(String title) { this.title = title; }

    public UUID getSpeaker() { return this.speaker; }

    public void setSpeaker(UUID speaker) { this.speaker = speaker; }

    public int getDuration() { return this.duration; }

    public void setDuration(int duration) { this.duration = duration; }

    @Override
    public String toString() {
        return '"' + title + "\" by " + speaker + "\n" +
                startTime + " to " + startTime.plusMinutes(duration) + " in " + room;
    }
}