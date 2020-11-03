package SchedulingSystem;

import CoreEntities.Event;
import CoreEntities.Schedule;
import CoreEntities.User;

import java.time.LocalTime;

public class EventManager {
    private Schedule mainSchedule;
    private EventAccessor eventAccessor;

    public EventManager(Schedule schedule) {
        this.mainSchedule = schedule;
        this.eventAccessor = new EventAccessor(schedule);
    }

    public void registerAttendee(User attendee, int eventNumber) throws IndexOutOfBoundsException {
        eventAccessor.retrieveSignupAbleEvents(attendee).retrieveEventByIndex(eventNumber).addAttendee(attendee);
    }

    public void removeAttendee(User attendee, int eventNumber) throws IndexOutOfBoundsException {
        eventAccessor.retrieveEventByAttendee(attendee).retrieveEventByIndex(eventNumber).removeAttendee(attendee);
    }

    private boolean checkRoomConflict(String room, LocalTime time, int duration) {
        for (Event event: eventAccessor.retrieveEventByTimeInterval(time, time.plusMinutes(duration))) {
            if (event.getRoom().equals(room)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkSpeakerConflict(User speaker, LocalTime time, int duration) {
        for (Event event: eventAccessor.retrieveEventByTimeInterval(time, time.plusMinutes(duration))) {
            if (event.getSpeaker().equals(speaker)) {
                return true;
            }
        }
        return false;
    }

    public void scheduleEvent(int capacity, String room, LocalTime startTime, String title, User speaker, int duration) {
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
