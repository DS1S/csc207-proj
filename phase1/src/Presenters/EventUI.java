package Presenters;

import LoginSystem.UserManager;
import SchedulingSystem.EventManager;

import java.util.List;
import java.util.Map;
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

    public void displayEvents(List<Map<String, Object>> eventList) {
        System.out.println("----------Events----------");
        StringBuilder sb = new StringBuilder();

        for (Map<String, Object> event : eventList) {
            sb.append(event.get("Title")).append("\n");
            sb.append(userManager.getUsernameWithUUID((UUID)event.get("Speaker"))).append("\n");
            sb.append(event.get("StartTime")).append(" to ").append(event.get("EndTime")).append("\n");
            sb.append(event.get("Room")).append("\n");
            sb.append(event.get("Registered")).append("/").append(event.get("Capacity")).append("\n");

            System.out.println(sb.toString());
            sb.setLength(0);
        }
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

    public void displayCancelSignupSuccess() {
        System.out.println("----------You have successfully unregistered from the event!----------\n\n");
    }

    public void displayCancelSignUpDouble() {
        System.out.println("----------You are not registered in this event!----------\n\n");
    }

    public void displayScheduleSuccess() {
        System.out.println("Your event has been successfully scheduled!\n\n");
    }
}
