package frontend;
import backend.entities.Statuses;
import backend.systems.usermangement.managers.UserManager;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Represents a InboxUI used for the message system UI.
 */
public class InboxUI extends MenuUI {
    private UserManager userManager;

    /**
     * Constructs a new instance of InboxUI using the given parameters.
     *
     * @param userManager The user manager used by the UI.
     */
    public InboxUI(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * Displays the user's inbox or a message indicating that their inbox is empty if their inbox is
     * empty.
     * @param inboxData The user's inbox data.
     */
    public void displayInbox(List<Map<String, Object>> inboxData) {
        int i = 1;

        for (Map<String, Object> data : inboxData) {
            StringBuilder sb = new StringBuilder();
            String recipient = userManager.getUsernameWithUUID((UUID) data.get("recipient"));
            String sender = userManager.getUsernameWithUUID((UUID) data.get("sender"));
            System.out.println("------------------------------------------------------");
            System.out.print("Message " + i + ": ");
            sb.append(data.get("title") + " | ");
            sb.append("From " + sender + " To " + recipient + " | ");
            sb.append(data.get("status"));
            System.out.println(sb);
            i += 1;
        }
        System.out.println("------------------------------------------------------");

        if (inboxData.size() == 0) {
            System.out.println("No mail in inbox! :(");
        }
    }

    /**
     * Displays the formatted details and body of the message represented by the given messageData.
     * @param messageData the data of the message to be displayed
     */
    public void displayMessage(Map<String, Object> messageData) {
        StringBuilder sb = new StringBuilder();
        String recipient = userManager.getUsernameWithUUID((UUID) messageData.get("recipient"));
        String sender = userManager.getUsernameWithUUID((UUID) messageData.get("sender"));
        System.out.println("------------------------------------------------------");
        sb.append("Title: " + messageData.get("title") + "\n");
        sb.append("Sent by " + sender + " To " + recipient  + " | " + messageData.get("timeSent") + "\n");
        sb.append("Body: " + messageData.get("body"));
        System.out.println(sb);
        System.out.println("------------------------------------------------------");
    }

    /**
     * Displays the messaging options available on the menu of an attendee type user.
     */
    public void displayAttendingMenuOptions() {
        displayMainOptions(true);
        displayExitOption(6);
    }

    /**
     * Displays the messaging options available on the menu of a speaker type user.
     */
    public void displayTalkSpeakerMenuOptions() {
        displayMainOptions(false);
        System.out.println("5. Reply to Attendee.");
        System.out.println("6. Message attendees of your talk(s).");
        displayExitOption(7);
    }

    /**
     * Displays the messaging options available on the menu of an organizer type user.
     */
    public void displaySchedulerMenuOptions() {
        displayMainOptions(true);
        System.out.println("6. Message all attendees.");
        System.out.println("7. Message all speakers.");
        displayExitOption(8);
    }

    /**
     * Prompts the user to write their message.
     */
    public void displayBodyPrompt() {
        System.out.print("Write your message: ");
    }

    /**
     * Prompts the user to write their title.
     */
    public void displayTitlePrompt() {
        System.out.println("Write your title: ");
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
    public void talksPrompt() {
        System.out.println("Enter Talks: ");
    }

    /**
     * Informs the user that the status of a message has changed.
     * @param status the new status of the message
     */
    public void displayStatusChanged(Statuses status) {
        System.out.println("Message has changed to: " + status);
    }

    /**
     * Informs the user that the message has been successfully deleted.
     */
    public void displayMessageDeleted() {
        System.out.println("Message has been deleted!");
    }

    private void displayExitOption ( int index){
        System.out.println(index + ". Return to main menu");
    }

    private void displayMainOptions(boolean canSendMessage) {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. View inbox.");
        if (canSendMessage) {
            System.out.println("2. View inbox by category.");
            System.out.println("3. Send message.");
            System.out.println("4. Change status of your messages.");
            System.out.println("5. Delete a message.");
        } else {
            System.out.println("2. View inbox by category.");
            System.out.println("3. Change status of your messages.");
            System.out.println("4. Delete a message.");
        }

    }
}