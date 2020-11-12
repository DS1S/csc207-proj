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

    public EventUI(UserManager userManager, UUID user) {
        this.userManager = userManager;
        this.user = user;
    }

    public void displayEvents(List<Map<String, Object>> eventList) {
        System.out.println("----------Events----------");
        StringBuilder sb = new StringBuilder();

        for (Map<String, Object> data : eventList) {
            sb.append(data.get("Title") + "\n");
            sb.append(userManager.getUsernameWithUUID((UUID)data.get("Speaker")) + "\n");
            sb.append(data.get("StartTime") + " to " + data.get("EndTime") + "\n");
            sb.append(data.get("Room") + "\n");
            sb.append(data.get("Registered") + "/" + data.get("Capacity") + "\n");

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
