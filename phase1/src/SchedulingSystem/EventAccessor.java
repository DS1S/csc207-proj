package SchedulingSystem;

import CoreEntities.Event;
import CoreEntities.Schedule;
import CoreEntities.User;

import java.time.LocalTime;

class EventAccessor {
    private Schedule mainSchedule;

    public EventAccessor(Schedule schedule) {
        this.mainSchedule = schedule;
    }

    public Schedule retrieveEventByTimeInterval(LocalTime start, LocalTime end) {
        return mainSchedule.retrieveEventByTimeInterval(start, end);
    }

    public Schedule retrieveEventBySpeaker(User speaker) {
        return mainSchedule.retrieveEventBySpeaker(speaker);
    }

    public Schedule retrieveEventByTitle(String title) {
        return mainSchedule.retrieveEventByTitle(title);
    }

    public Schedule retrieveEventByAttendee(User attendee) {
        return mainSchedule.retrieveEventByAttendee(attendee);
    }

    public Schedule retrieveSignupAbleEvents(User attendee) {
        return mainSchedule.retrieveSignupAbleEvents(attendee);
    }
}
