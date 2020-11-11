package SchedulingSystem;

import CoreEntities.Event;

import java.time.LocalTime;
import java.util.UUID;

public class EventScheduler {

    private boolean checkRoomConflict(Schedule schedule, String room, LocalTime time, int duration) {
        for (Event event: schedule.retrieveEventsByTimeInterval(time, time.plusMinutes(duration))) {
            if (event.getRoom().equals(room)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkSpeakerConflict(Schedule schedule, UUID speaker, LocalTime time, int duration) {
        for (Event event: schedule.retrieveEventsByTimeInterval(time, time.plusMinutes(duration))) {
            if (event.getSpeaker().equals(speaker)) {
                return true;
            }
        }
        return false;
    }

    public void scheduleEvent(Schedule schedule, int capacity, String room, LocalTime startTime, String title, UUID speaker, int duration) {
        if (!checkRoomConflict(schedule, room, startTime, duration) && !checkSpeakerConflict(schedule, speaker, startTime, duration)) {
            schedule.addEvent(new Event(capacity, room, startTime, title, speaker, duration));
        }
    }

    public void cancelEvent(Schedule schedule, int eventNumber) {
        schedule.removeEventByIndex(eventNumber);
    }

    public void rescheduleEvent(Schedule schedule, int eventNumber, LocalTime newStartTime) {
        Event event = schedule.retrieveEventByIndex(eventNumber);
        schedule.removeEventByIndex(eventNumber);
        event.setStartTime(newStartTime);
        schedule.addEvent(event);
    }
}
