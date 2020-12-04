package frontend;
import backend.systems.usermangement.managers.UserManager;
import backend.systems.messaging.managers.MessageManager;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Represents a InboxUI used for the message system UI.*/
public class InboxUI extends MenuUI {
    private UserManager userManager;
    private MessageManager messageManager;

    /**
     * Constructs a new instance of InboxUI using the given parameters.
     *
     * @param userManager The user manager used by the UI.
     */
    public InboxUI(UserManager userManager, MessageManager messageManager) {
        this.userManager = userManager;
        this.messageManager = messageManager;
    }

    /**
     * Displays the user's inbox and a message indicating that their inbox is empty if their inbox is
     * empty.
     *
     * @param inboxData The user's inbox data.
     */
    public void displayInbox(List<Map<String, Object>> inboxData) {
        int i = 1;

        for (Map<String, Object> data : inboxData) {
            StringBuilder sb = new StringBuilder();
            String recipient = userManager.getUsernameWithUUID((UUID) data.get("recipient"));
            String sender = userManager.getUsernameWithUUID((UUID) data.get("sender"));
            System.out.println("------------------------------------------------------");
            System.out.println("Message " + i);
            sb.append("From: " + sender + "\nTo: " + recipient + "\n\n");
            sb.append(data.get("title") + "\n");
            System.out.print(sb);
            System.out.println("------------------------------------------------------");
            i += 1;
        }

        if (inboxData.size() == 0) {
            System.out.println("Your inbox is empty! :(");
        }
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

    private void displayBaseInboxOptions() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. View inbox.");
        System.out.println("5. View a message.");
        System.out.println("6. Mark a message as unread.");
        System.out.println("7. Archive a message.");
        System.out.println("8. Delete a message.");
        System.out.println("9. View archived messages.");
    }

    /**
     * Prompts the user to enter the title of the message they want to delete.
     */
    public void deletionPrompt() {
        System.out.println("Enter the title of the message you wish to delete: ");
    }

    /**
     * Prompts the user to enter the title of the message they want to view.
     */
    public void viewMessagePrompt() {
        System.out.println("Enter the title of the message you wish to view: ");
    }

    /**
     * Displays to the user the message that they wanted to view.
     */
    public void displayMessage() {
        String body = messageManager.getToDisplay();
        System.out.println(body);
    }

    public void markMessageUnreadPrompt(){
        System.out.println("Enter the title of the message you wish to mark as unread: ");
    }

    public void markedAsUnreadPrompt(){
        System.out.println("Message marked unread!");
    }

    public void archiveMessagePrompt() {
        System.out.println("Enter the title of the message you wish to archive: ");
    }

    public void messageArchivedPrompt(){
        System.out.println("Message archived!");
    }

    private void displayExitOption ( int index){
        System.out.println(index + ". Return to main menu");
    }

        /**
         * Informs the user that their message was successfully deleted.
         */
        public void deletedPrompt(){
            System.out.println("Message deleted!");
        }
    }