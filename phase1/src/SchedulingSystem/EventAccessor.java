package SchedulingSystem;

import CoreEntities.Event;
import CoreEntities.Schedule;
import CoreEntities.User;

import java.time.LocalTime;
import java.util.UUID;

class EventAccessor {
    private Schedule mainSchedule;

    public EventAccessor(Schedule schedule) {
        this.mainSchedule = schedule;
    }

    public Schedule retrieveEventByTimeInterval(LocalTime start, LocalTime end) {
        return mainSchedule.retrieveEventByTimeInterval(start, end);
    }

    public Schedule retrieveEventBySpeaker(UUID speaker) {
        return mainSchedule.retrieveEventBySpeaker(speaker);
    }

    public Schedule retrieveEventByTitle(String title) {
        return mainSchedule.retrieveEventByTitle(title);
    }

    public Schedule retrieveEventByAttendee(UUID attendee) {
        return mainSchedule.retrieveEventByAttendee(attendee);
    }

    public Schedule retrieveSignupAbleEvents(UUID attendee) {
        return mainSchedule.retrieveSignupAbleEvents(attendee);
    }

    public void registerAttendee(UUID attendee, int eventNumber) throws IndexOutOfBoundsException {
        retrieveEventByAttendee(attendee).retrieveEventByIndex(eventNumber).addAttendee(attendee);
    }

    public void removeAttendee(UUID attendee, int eventNumber) throws IndexOutOfBoundsException {
        retrieveEventByAttendee(attendee).retrieveEventByIndex(eventNumber).removeAttendee(attendee);
    }
}
