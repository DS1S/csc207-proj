package frontend;

import backend.systems.usermangement.managers.UserManager;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A class that displays prompts and messages for event related actions.
 */
public class EventUI extends MenuUI {
    private UserManager userManager;

    /**
     * Constructs a new EventUI that uses the given user manager.
     * @param userManager The user manager used by the EventUI.
     */
    public EventUI(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * Displays a formatted list of events based on the given list of extracted event data.
     * @param eventList The list of extracted event data for the events to be displayed.
     */
    public void displayEvents(List<Map<String, Object>> eventList) {
        if (eventList.isEmpty()) {
            System.out.println("There are no events to display");
        }
        else {
            System.out.println("------------------------Events------------------------");
            StringBuilder sb = new StringBuilder();
            int i = 1;
            for (Map<String, Object> data : eventList) {
                sb.append("Event " + i + "\n");
                formatEventData(sb, data);
                System.out.print(sb);
                System.out.println("------------------------------------------------------");
                sb.setLength(0);
                i += 1;
            }
        }
    }

    private void formatEventData(StringBuilder sb, Map<String, Object> data) {
        sb.append("\"" + data.get("Title") + "\"" + "\n");
        List<?> speakers = (List<?>) data.get("Speaker");
        if (!(speakers.isEmpty())){
            sb.append("Hosted by: ");
            for (Object speakerUUID: speakers) {
                sb.append(userManager.getNameWithUUID((UUID)speakerUUID));
            }
            sb.append("\n");
        }
        sb.append(data.get("StartTime") + " to " + data.get("EndTime") + "\n");
        sb.append("Room: " + data.get("Room") + "\n");
        sb.append(data.get("Registered") + "/" + data.get("Capacity") + " spots filled" + "\n");
    }

    /**
     * Displays a list of options pertaining to the scheduling of events.
     */
    public void displayScheduleOptions() {
        System.out.println("\nWhat would you like to do?");
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
        System.out.println("\nWhat would you like to do?");
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
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. View all events.");
        System.out.println("2. View events you are speaking at.");
        System.out.println("3. Return to main menu.");
    }

    /**
     *  Displays a list of options pertaining to displaying event categories for printing.
     */
    public void displayCategoryRetrieverOptions() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. View all events for a day.");
        System.out.println("2. View all events by a speaker.");
        System.out.println("3. View all the events you have signed up for.");
    }

    /**
     * Displays a message informing the user that they have successfully registered for an event.
     */
    public void displaySignupSuccess() {
        System.out.println("You have successfully registered for the event!");
    }

    /**
     * Displays a message informing the user that they have successfully unregistered for an event.
     */
    public void displayCancelSignupSuccess() {
        System.out.println("You have successfully unregistered from the event!");
    }

    /**
     * Displays a message prompting the user to select an event.
     */
    public void displayEnterIndexEvent() {
        System.out.println("Please enter the number of the event you would like to select.\n (e.g. to select Event 1, enter 1.)");
    }

    /**
     * Displays a message informing the user that they have started to schedule a new event.
     */
    public void displayScheduleStart() { System.out.println("Enter the required information to schedule a new event.\n"); }

    /**
     * Displays a message prompting the user for the event's title.
     */
    public void displayTitlePrompt() { System.out.println("Title: "); }

    /**
     * Displays a message asking whether the user would like to add a speaker to the event.
     */
    public void displayFirstSpeakerPrompt() { System.out.println("Add a speaker to this event? (Y/N)"); }

    /**
     * Displays a message asking whether the user would like to add another speaker to the event.
     */
    public void displayAnotherSpeakerPrompt() { System.out.println("Add another speaker to this event? (Y/N)"); }

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
     * Displays a message prompting the user for the event's time.
     */
    public void displayTimePrompt() { System.out.println("Time (format HH:MM): "); }

    /**
     * Displays a message informing the user that they have entered an invalid time.
     */
    public void displayInvalidTime() { System.out.println("Invalid input: Please use the format HH:MM."); }

    /**
     * Displays a message prompting the user for the event's duration.
     */
    public void displayDurationPrompt() { System.out.println("Duration (in minutes 1-180): "); }

    /**
     * Displays a message informing the user that they have entered an invalid duration.
     */
    public void displayInvalidDuration() { System.out.println("Invalid Duration!"); }

    /**
     * Displays a message informing the user that they have successfully scheduled an event.
     */
    public void displayScheduleSuccess() {
        System.out.println("Your event has been successfully scheduled!");
    }

    /**
     * Displays a message informing the user that the event was not scheduled due to conflicts, and
     * displays conflicting events.
     */
    public void displayScheduleFailure(List<Map<String, Object>> eventList) {
        System.out.println("Your event was not scheduled. Your event conflicts with the following existing events:\n");
        displayEvents(eventList);
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
        System.out.println("Your event has been successfully rescheduled!");
    }

    /**
     * Displays a message informing the user that the event was not rescheduled due to conflicts, and
     * displays conflicting events.
     */
    public void displayRescheduleFailure(List<Map<String, Object>> eventList) {
        System.out.println("Your event was not rescheduled. Your event conflicts with the following existing events:\n");
        displayEvents(eventList);
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
