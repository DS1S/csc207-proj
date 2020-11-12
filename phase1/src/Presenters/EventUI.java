package Presenters;

import LoginSystem.UserManager;
import SchedulingSystem.EventManager;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EventUI {
    private UserManager userManager;

    public EventUI(UserManager userManager) {
        this.userManager = userManager;
    }

    public void displayEvents(List<Map<String, Object>> eventList) {
        System.out.println("----------Events----------");
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Map<String, Object> data : eventList) {
            i += 1;
            sb.append("Event " + i + "\n");
            sb.append(data.get("Title") + "\n");
            sb.append(userManager.getUsernameWithUUID((UUID)data.get("Speaker")) + "\n");
            sb.append(data.get("StartTime") + " to " + data.get("EndTime") + "\n");
            sb.append(data.get("Room") + "\n");
            sb.append(data.get("Registered") + "/" + data.get("Capacity") + "\n");
            System.out.println(sb);
            System.out.println("----------------------------------");
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

    public void displayIndexPrompt() { System.out.println("Enter the number of the event you wish to select: ");}

    public void displayInvalidIndex() { System.out.println("You have entered an invalid index."); }

    public void displayScheduleStart() { System.out.println("Enter the required information to schedule a new event.\n"); }

    public void displayTitlePrompt() { System.out.println("Title: "); }

    public void displaySpeakerPrompt() { System.out.println("Speaker's Username: "); }

    public void displayInvalidSpeaker() { System.out.println("Invalid input: Speaker with the given username does not exist!"); }

    public void displayRoomPrompt() { System.out.println("Room: "); }

    public void displayCapacityPrompt() { System.out.println("Event Capacity: "); }

    public void displayInvalidCapacity() { System.out.println("Invalid input: Capacity must be a number!"); }

    public void displayTimePrompt() { System.out.println("Start Time (format HH:MM): "); }

    public void displayInvalidTime() { System.out.println("Invalid input: Please use the format HH:MM."); }

    public void displayDurationPrompt() { System.out.println("Duration (in minutes): "); }

    public void displayInvalidDuration() { System.out.println("Invalid input: Duration must be a number!"); }

    public void displayScheduleSuccess() {
        System.out.println("Your event has been successfully scheduled!\n\n");
    }

    public void displayScheduleFailure(List<Map<String, Object>> eventList) {
        System.out.println("Your event was not scheduled. Your event conflicts with the following existing events:\n");
        displayEvents(eventList);
    }

    public void displayRescheduleStart() {
        System.out.println("Select the event to be rescheduled.");
        System.out.println("Then, enter the required information to reschedule the event.");
    }

    public void displayRescheduleSuccess() {
        System.out.println("Your event has been successfully rescheduled!\n\n");
    }

    public void displayRescheduleFailure(List<Map<String, Object>> eventList) {
        System.out.println("Your event was not rescheduled. Your event conflicts with the following existing events:\n");
        displayEvents(eventList);
    }

    public void displayCancelStart() {
        System.out.println("Select the event to be cancelled.");
    }

    public void displayCancelSuccess() {
        System.out.println("The selected event has been cancelled.");
    }
}
