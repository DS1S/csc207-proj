package SchedulingSystem;

import CoreEntities.Schedule;

import java.time.LocalTime;
import java.util.UUID;

public class EventManager {
    private Schedule mainSchedule = new Schedule();
    private EventAccessor eventAccessor = new EventAccessor();
    private EventScheduler eventScheduler = new EventScheduler();

    public void registerAttendee(UUID attendee, int eventNumber) throws IndexOutOfBoundsException {
        eventAccessor.registerAttendee(mainSchedule, attendee, eventNumber);
    }

    public void removeAttendee(UUID attendee, int eventNumber) throws IndexOutOfBoundsException {
        eventAccessor.removeAttendee(mainSchedule, attendee, eventNumber);
    }

    public void scheduleEvent(String room, LocalTime startTime, String title, UUID speaker) {
        eventScheduler.scheduleEvent(mainSchedule, 2, room, startTime, title, speaker, 60);
    }

    public void cancelEvent(int eventNumber) {
        eventScheduler.cancelEvent(mainSchedule, eventNumber);
    }

    public void rescheduleEvent(int eventNumber, LocalTime newStartTime) {
        eventScheduler.rescheduleEvent(mainSchedule, eventNumber, newStartTime);
    }
}
