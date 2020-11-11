package SchedulingSystem;

import java.time.LocalTime;
import java.util.UUID;

public class EventManager {
    private Schedule mainSchedule = new Schedule();
    private EventSignUp eventSignUp = new EventSignUp();
    private EventScheduler eventScheduler = new EventScheduler();

    public Schedule retrieveEventsByTimeInterval(LocalTime start, LocalTime end) {
        return mainSchedule.retrieveEventsByTimeInterval(start, end);
    }

    public Schedule retrieveEventsBySpeaker(UUID speaker) {
        return mainSchedule.retrieveEventsBySpeaker(speaker);
    }

    public Schedule retrieveEventsByTitle(String title) {
        return mainSchedule.retrieveEventsByTitle(title);
    }

    public Schedule retrieveEventsByAttendee(UUID attendee) {
        return mainSchedule.retrieveEventsByAttendee(attendee);
    }

    public Schedule retrieveSignupAbleEvents(UUID attendee) {
        return mainSchedule.retrieveSignupAbleEvents(attendee);
    }

    public void registerAttendee(UUID attendee, int eventNumber) throws IndexOutOfBoundsException {
        eventSignUp.registerAttendee(mainSchedule, attendee, eventNumber);
    }

    public void removeAttendee(UUID attendee, int eventNumber) throws IndexOutOfBoundsException {
        eventSignUp.removeAttendee(mainSchedule, attendee, eventNumber);
    }

    public void scheduleEvent(int capacity, String room, LocalTime startTime, String title, UUID speaker, int duration) {
        eventScheduler.scheduleEvent(mainSchedule, capacity, room, startTime, title, speaker, duration);
    }

    public void cancelEvent(int eventNumber) {
        eventScheduler.cancelEvent(mainSchedule, eventNumber);
    }

    public void rescheduleEvent(int eventNumber, LocalTime newStartTime) {
        eventScheduler.rescheduleEvent(mainSchedule, eventNumber, newStartTime);
    }
}
