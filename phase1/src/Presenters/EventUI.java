package Presenters;

import CoreEntities.Event;
import CoreEntities.Users.Speaker;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class EventUI {
    private EventManager eventManager;
    private UUID user;

    public EventUI(Schedule schedule, UUID user) {
        this.user = user;
        this.events = schedule.events;
        //TODO: make these lists of events with re-worked schedule stuffs
        this.attendeeEvents = schedule.retrieveEventsByAttendee(user).events;
    }

    public void displayList(List<Event> eventList) {
        int i = 1;
        for (Event event : eventList) {
            // Prints the event name, speaker, start and end times, room, and capacity, in that order.
            System.out.println("Event #" + i + ": " + event.getTitle());
            System.out.println("Speaker: " + event.getSpeaker());
            System.out.println("From " + event.getStartTime().toString() + " to " + event.getEndTime().toString());
            System.out.println(event.getRoom());
            System.out.println(event.getAttendees() + "/" + event.getCapacity() + " participants\n\n");
            i += 1;
        }
    }

    public void displayAllEvents() {
        System.out.println("----------All Events----------\n\n");
        displayList(events);
    }

    public void displayAttendeeEvents() {
        System.out.println("----------Events you are attending---------\n\n");
        displayList(attendeeEvents);
    }

    public void displayEventsByTimeInterval(LocalTime startTime, LocalTime endTime) {
        List<Event> timeIntervalEvents = schedule.retrieveEventsByTimeInterval(startTime, endTime).events;
        System.out.println("----------Events from " + startTime + " to " + endTime + "----------\n\n");
        displayList(timeIntervalEvents);
    }

    public void displayEventsBySpeaker(Speaker speaker) {
        List<Event> speakerEvents = schedule.retrieveEventsBySpeaker(speaker.getUUID()).events;
        System.out.println("----------" + speaker.getUsername() + "'s Events----------\n\n");
        displayList(speakerEvents);
    }

    public void displaySignupableEvents() {
        List<Event> signupableEvents = schedule.retrieveSignupAbleEvents(user).events;
        System.out.println("----------Available Events----------\n\n");
        displayList(signupableEvents);
    }
}
