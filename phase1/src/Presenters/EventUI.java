package Presenters;

import CoreEntities.Event;
import CoreEntities.Users.Speaker;
import SchedulingSystem.EventManager;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class EventUI {
    private EventManager eventManager;
    private UUID user;

    public EventUI(EventManager eventManager, UUID user) {
        this.eventManager = eventManager;
        this.user = user;
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
        displayList(eventManager.retrieveAllEvents());
    }

    public void displayAttendeeEvents() {
        System.out.println("----------Events you are attending---------\n\n");
        displayList(eventManager.retrieveEventsByAttendee(user));
    }

    public void displayEventsByTimeInterval(LocalTime startTime, LocalTime endTime) {
        List<Event> timeIntervalEvents = eventManager.retrieveEventsByTimeInterval(startTime, endTime);
        System.out.println("----------Events from " + startTime + " to " + endTime + "----------\n\n");
        displayList(timeIntervalEvents);
    }

    public void displayEventsBySpeaker(Speaker speaker) {
        List<Event> speakerEvents = eventManager.retrieveEventsBySpeaker(speaker.getUUID());
        System.out.println("----------" + speaker.getUsername() + "'s Events----------\n\n");
        displayList(speakerEvents);
    }

    public void displaySignupableEvents() {
        List<Event> signupableEvents = eventManager.retrieveSignupAbleEvents(user);
        System.out.println("----------Available Events----------\n\n");
        displayList(signupableEvents);
    }
}
