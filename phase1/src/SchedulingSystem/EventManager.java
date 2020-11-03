package SchedulingSystem;

import CoreEntities.Schedule;

import java.time.LocalTime;
import java.util.UUID;

public class EventManager {
    private Schedule mainSchedule;
    private EventAccessor eventAccessor;
    private EventScheduler eventScheduler;

    public EventManager(Schedule schedule) {
        this.mainSchedule = schedule;
        this.eventAccessor = new EventAccessor();
        this.eventScheduler = new EventScheduler();
    }

    public void registerAttendee(UUID attendee, int eventNumber) throws IndexOutOfBoundsException {
        eventAccessor.registerAttendee(mainSchedule, attendee, eventNumber);
    }

    public void removeAttendee(UUID attendee, int eventNumber) throws IndexOutOfBoundsException {
        eventAccessor.removeAttendee(mainSchedule, attendee, eventNumber);
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
