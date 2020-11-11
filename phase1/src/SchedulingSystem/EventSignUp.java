package SchedulingSystem;

import java.util.UUID;

class EventSignUp {

    public void registerAttendee(Schedule schedule, UUID attendee, int eventNumber) throws IndexOutOfBoundsException {
        schedule.retrieveSignupAbleEvents(attendee).retrieveEventByIndex(eventNumber).addAttendee(attendee);
    }

    public void removeAttendee(Schedule schedule,UUID attendee, int eventNumber) throws IndexOutOfBoundsException {
        schedule.retrieveEventsByAttendee(attendee).retrieveEventByIndex(eventNumber).removeAttendee(attendee);
    }
}
