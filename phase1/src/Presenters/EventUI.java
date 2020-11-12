package Presenters;

import LoginSystem.UserManager;
import SchedulingSystem.EventManager;
import java.util.UUID;

public class EventUI {
    private EventManager eventManager;
    private UserManager userManager;
    private UUID user;

    public EventUI(EventManager eventManager, UserManager userManager, UUID user) {
        this.eventManager = eventManager;
        this.userManager = userManager;
        this.user = user;
    }

    public void displayAllEvents() {
        System.out.println("----------All Events----------\n\n");
        System.out.println(eventManager.retrieveAllEvents());
    }

    public void displayAttendeeEvents() {
        System.out.println("----------Events you are attending---------\n\n");
        System.out.println(eventManager.retrieveEventsByAttendee(user));
    }

    public void displayEventsBySpeaker(String speakerName) {
        System.out.println("----------" + speakerName + "'s Events----------\n\n");
        System.out.println(eventManager.retrieveEventsBySpeaker(userManager.getUUIDWithUsername(speakerName)));
    }

    public void displaySignupableEvents() {
        System.out.println("----------Available Events----------\n\n");
        System.out.println(eventManager.retrieveSignupAbleEvents(user));
    }

    public void displaySignUpFull() {
        System.out.println("-------This event is full!-------\n\n");
    }

    public void displaySignupSuccess() {
        System.out.println("----------You have successfully registered for the event!----------\n\n");
    }

    public void displaySignUpDouble() {
        System.out.println("----------You are already registered in this event!----------\n\n");
    }

}
