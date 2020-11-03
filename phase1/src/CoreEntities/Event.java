package CoreEntities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.time.LocalTime;

public class Event implements Serializable {
    private int capacity;
    private List<User> attendees;
    private String room;
    private LocalTime startTime;
    private String title;
    private User speaker;
    private int duration;

    public Event(int capacity, String room, LocalTime startTime, String title, User speaker) {
        this.capacity = capacity;
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

    public List<User> getAttendees() { return this.attendees; }

    public void addAttendee(User attendee) { this.attendees.add(attendee); }

    public void removeAttendee(User attendee) { this.attendees.remove(attendee); }
    
    public boolean checkAttendee(User attendee) { return this.attendees.contains(attendee); }

    public String getRoom() { return this.room; }

    public void setRoom(String room) { this.room = room; }

    public LocalTime getStartTime() { return this.startTime; }

    public void setStartTime(LocalTime time) { this.startTime = time; }

    public LocalTime getEndTime() { return startTime.plusMinutes(duration); }

    public String getTitle() { return this.title; }

    public void setTitle(String title) { this.title = title; }

    public User getSpeaker() { return this.speaker; }

    public void setSpeaker(User speaker) { this.speaker = speaker; }

    public int getDuration() { return this.duration; }

    public void setDuration(int duration) { this.duration = duration; }

    @Override
    public String toString() {
        return '"' + title + "\" by " + speaker + "\n" +
                startTime + " to " + startTime.plusMinutes(duration) + " in " + room;
    }
}