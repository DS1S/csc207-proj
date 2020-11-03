package SchedulingSystem;

import CoreEntities.Event;
import CoreEntities.Schedule;

import java.time.LocalTime;
import java.util.UUID;

public class EventManager {
    private Schedule mainSchedule;
    private EventAccessor eventAccessor;

    public EventManager(Schedule schedule) {
        this.mainSchedule = schedule;
        this.eventAccessor = new EventAccessor(schedule);
    }

    public void registerAttendee(UUID attendee, int eventNumber) throws IndexOutOfBoundsException {
        eventAccessor.registerAttendee(attendee, eventNumber);
    }

    public void removeAttendee(UUID attendee, int eventNumber) throws IndexOutOfBoundsException {
        eventAccessor.removeAttendee(attendee, eventNumber);
    }

    private boolean checkRoomConflict(String room, LocalTime time, int duration) {
        for (Event event: eventAccessor.retrieveEventByTimeInterval(time, time.plusMinutes(duration))) {
            if (event.getRoom().equals(room)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkSpeakerConflict(UUID speaker, LocalTime time, int duration) {
        for (Event event: eventAccessor.retrieveEventByTimeInterval(time, time.plusMinutes(duration))) {
            if (event.getSpeaker().equals(speaker)) {
                return true;
            }
        }
        return false;
    }

    public void scheduleEvent(int capacity, String room, LocalTime startTime, String title, UUID speaker, int duration) {
        if (!checkRoomConflict(room, startTime, duration) && !checkSpeakerConflict(speaker, startTime, duration)) {
            mainSchedule.addEvent(new Event(capacity, room, startTime, title, speaker, duration));
        }
    }

    public void cancelEvent(int eventNumber) {
        mainSchedule.removeEventByIndex(eventNumber);
    }

    public void rescheduleEvent(int eventNumber, LocalTime newStartTime) {
        Event event = mainSchedule.retrieveEventByIndex(eventNumber);
        mainSchedule.removeEventByIndex(eventNumber);
        event.setStartTime(newStartTime);
        mainSchedule.addEvent(event);
    }
}
