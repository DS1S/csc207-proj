package SchedulingSystem;

import CoreEntities.Event;
import CoreEntities.Schedule;
import CoreEntities.User;

import java.time.LocalTime;
import java.util.UUID;

class EventAccessor {

    public Schedule retrieveEventByTimeInterval(Schedule schedule, LocalTime start, LocalTime end) {
        return schedule.retrieveEventByTimeInterval(start, end);
    }

    public Schedule retrieveEventBySpeaker(Schedule schedule, UUID speaker) {
        return schedule.retrieveEventBySpeaker(speaker);
    }

    public Schedule retrieveEventByTitle(Schedule schedule,String title) {
        return schedule.retrieveEventByTitle(title);
    }

    public Schedule retrieveEventByAttendee(Schedule schedule,UUID attendee) {
        return schedule.retrieveEventByAttendee(attendee);
    }

    public Schedule retrieveSignupAbleEvents(Schedule schedule,UUID attendee) {
        return schedule.retrieveSignupAbleEvents(attendee);
    }

    public void registerAttendee(Schedule schedule, UUID attendee, int eventNumber) throws IndexOutOfBoundsException {
        retrieveEventByAttendee(schedule, attendee).retrieveEventByIndex(eventNumber).addAttendee(attendee);
    }

    public void removeAttendee(Schedule schedule,UUID attendee, int eventNumber) throws IndexOutOfBoundsException {
        retrieveEventByAttendee(schedule, attendee).retrieveEventByIndex(eventNumber).removeAttendee(attendee);
    }
}
