package Presenters;

import LoginSystem.UserManager;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A class that displays prompts and messages for event related actions.
 */
public class EventUI {
    private UserManager userManager;

    /**
     * Constructs a new EventUI that uses the given user manager.
     * @param userManager the user manager used by the EventUI
     */
    public EventUI(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * Displays a formatted list of events based on the given list of extracted event data.
     * @param eventList the list of extracted event data for the events to be displayed
     */
    public void displayEvents(List<Map<String, Object>> eventList) {
        if (eventList.isEmpty()){
            System.out.println("There are no events scheduled");
        }
        else {
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
    }

    /**
     * Displays a list of options pertaining to the scheduling of events.
     */
    public void displayScheduleOptions() {
        System.out.println("What would you like to do?\n");
        System.out.println("1. View all events.");
        System.out.println("2. Schedule a new event.");
        System.out.println("3. Reschedule an existing event.");
        System.out.println("4. Cancel an existing event.");
        System.out.println("5. Return to main menu.");
    }

    /**
     * Displays a list of options pertaining to signing up for events.
     */
    public void displaySignupOptions() {
        System.out.println("What would you like to do?\n");
        System.out.println("1. View all events.");
        System.out.println("2. Sign up for an event.");
        System.out.println("3. Cancel a registered event.");
        System.out.println("4. View events you have signed up for.");
        System.out.println("5. Return to main menu.");
    }

    /**
     * Displays a list of options pertaining to speaking at events.
     */
    public void displaySpeakerOptions() {
        System.out.println("What would you like to do?\n");
        System.out.println("1. View all events.");
        System.out.println("2. View events you are speaking at.");
        System.out.println("3. Return to main menu.");
    }

    public void displaySignUpFull() {
        System.out.println("-------This event is full!-------\n\n");
    }

    /**
     * Displays a message informing the user that they have successfully registered for an event.
     */
    public void displaySignupSuccess() {
        System.out.println("----------You have successfully registered for the event!----------\n\n");
    }

    /**
     * Displays a message informing the user that they have successfully unregistered for an event.
     */
    public void displayCancelSignupSuccess() {
        System.out.println("----------You have successfully unregistered from the event!----------\n\n");
    }

    /**
     * Displays a message prompting the user to select an event.
     */
    public void displayEnterIndexEvent() {
        System.out.println("----------Please select an event number that you want to register!----------\n\n");
    }

    /**
     * Displays a message prompting the user to select an option.
     */
    public void displayIndexPrompt() { System.out.println("Enter the number of the option you wish to select: ");}

    /**
     * Displays a message informing the user that they have entered an invalid number.
     */
    public void displayInvalidIndex() { System.out.println("You have entered an invalid number."); }

    /**
     * Displays a message informing the user that they have started to schedule a new event.
     */
    public void displayScheduleStart() { System.out.println("Enter the required information to schedule a new event.\n"); }

    /**
     * Displays a message prompting the user for the event's title.
     */
    public void displayTitlePrompt() { System.out.println("Title: "); }

    /**
     * Displays a message prompting the user for the speaker's username.
     */
    public void displaySpeakerPrompt() { System.out.println("Speaker's Username: "); }

    /**
     * Displays a message informing the user that they have entered an invalid speaker username.
     */
    public void displayInvalidSpeaker() { System.out.println("Invalid input: Speaker with the given username does not exist!"); }

    /**
     * Displays a message prompting the user for the event's room.
     */
    public void displayRoomPrompt() { System.out.println("Room: "); }

    /**
     * Displays a message prompting the user for the event's capacity.
     */
    public void displayCapacityPrompt() { System.out.println("Event Capacity: "); }

    /**
     * Displays a message informing the user that they have entered an invalid capacity.
     */
    public void displayInvalidCapacity() { System.out.println("Invalid input: Capacity must be a number!"); }

    /**
     * Displays a message prompting the user for the event's start time.
     */
    public void displayTimePrompt() { System.out.println("Start Time (format HH:MM): "); }

    /**
     * Displays a message informing the user that they have entered an invalid time.
     */
    public void displayInvalidTime() { System.out.println("Invalid input: Please use the format HH:MM."); }

    /**
     * Displays a message prompting the user for the event's duration.
     */
    public void displayDurationPrompt() { System.out.println("Duration (in minutes): "); }

    /**
     * Displays a message informing the user that they have entered an invalid duration.
     */
    public void displayInvalidDuration() { System.out.println("Invalid input: Duration must be a number!"); }

    /**
     * Displays a message informing the user that they have successfully scheduled an event.
     */
    public void displayScheduleSuccess() {
        System.out.println("Your event has been successfully scheduled!\n\n");
    }

    /**
     * Displays a message informing the user that the event was not scheduled due to conflicts, and
     * displays conflicting events.
     */
    public void displayScheduleFailure(List<Map<String, Object>> eventList) {
        System.out.println("Your event was not scheduled. Your event conflicts with the following existing events:\n");
    }

    /**
     * Displays a message informing the user that they have started to reschedule a new event.
     */
    public void displayRescheduleStart() {
        System.out.println("Select the event to be rescheduled.");
        System.out.println("Then, enter the required information to reschedule the event.");
    }

    /**
     * Displays a message informing the user that they have successfully rescheduled an event.
     */
    public void displayRescheduleSuccess() {
        System.out.println("Your event has been successfully rescheduled!\n\n");
    }

    /**
     * Displays a message informing the user that the event was not rescheduled due to conflicts, and
     * displays conflicting events.
     */
    public void displayRescheduleFailure(List<Map<String, Object>> eventList) {
        System.out.println("Your event was not rescheduled. Your event conflicts with the following existing events:\n");
    }

    /**
     * Displays a message prompting the user to select an event to be cancelled.
     */
    public void displayCancelStart() {
        System.out.println("Select the event to be cancelled.");
    }

    /**
     * Displays a message informing the user that the event was successfully cancelled.
     */
    public void displayCancelSuccess() {
        System.out.println("The selected event has been cancelled.");
    }
}
