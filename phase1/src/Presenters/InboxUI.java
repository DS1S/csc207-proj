package Presenters;
import LoginSystem.UserManager;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * An InboxUI class that extends ErrorUI.*/
public class InboxUI extends ErrorUI {
    private UserManager userManager;

    /**
     * Constructs a new instance of InboxUI using the given parameters.
     * @param userManager The user manager used by the UI.
     */
    public InboxUI(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * Displays the user's inbox and a message indicating that their inbox is empty if their inbox is
     * empty.
     * @param inboxData The user's inbox data.
     */
    public void displayInbox(List<Map<String, Object>> inboxData) {
        int i = 1;

        for(Map<String, Object> data : inboxData) {
            StringBuilder sb = new StringBuilder();
            String recipient = userManager.getUsernameWithUUID((UUID) data.get("recipient"));
            String sender = userManager.getUsernameWithUUID((UUID) data.get("sender"));
            System.out.println("------------------------------------------------------");
            System.out.println("Message " + i);
            sb.append("From: " + sender + "\nTo: " + recipient + "\n\n");
            sb.append(data.get("body") + "\n");
            System.out.print(sb);
            System.out.println("------------------------------------------------------");
            i += 1;
        }

        if (inboxData.size() == 0) { System.out.println("Your inbox is empty! :("); }
    }

    /**
     * Displays the messaging options available on the menu of an attendee type user.
     */
    public void displayAttendingMenuOptions() {
        displayBaseInboxOptions();
        System.out.println("2. Send Message.");
        displayExitOption(3);
    }

    /**
     * Displays the messaging options available on the menu of a speaker type user.
     */
    public void displayTalkSpeakerMenuOptions() {
        displayBaseInboxOptions();
        System.out.println("2. Reply to Attendee.");
        System.out.println("3. Message attendees of your talk(s).");
        displayExitOption(4);
    }

    /**
     * Displays the messaging options available on the menu of an organizer type user.
     */
    public void displaySchedulerMenuOptions() {
        displayBaseInboxOptions();
        System.out.println("2. Send Message.");
        System.out.println("3. Message all attendees.");
        System.out.println("4. Message all speakers.");
        displayExitOption(5);
    }

    /**
     * Prompts the user to write their message.
     */
    public void displayBodyPrompt() {
        System.out.print("Write your message: ");
    }

    /**
     * Prompts the user to indicate the message's intended recipeint(s).
     */
    public void displayUserPrompt() {
        System.out.print("Enter Recipient(s), comma separated:");
    }

    /**
     * Informs the user that their message was successfully sent.
     */
    public void sentPrompt() {
        System.out.println("Message sent!");
    }

    /**
     * Prompts the user to enter talk(s).
     */
    public void talksPrompt() { System.out.println("Enter Talks: "); }

    private void displayBaseInboxOptions() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. View Inbox.");
    }

    private void displayExitOption(int index) {
        System.out.println(index + ". Return to main menu");
    }
}