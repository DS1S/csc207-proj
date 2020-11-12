package Presenters;

import CoreEntities.Message;
import CoreEntities.Users.User;
import MessagingSystem.MessageManager;
import java.util.UUID;

public class InboxUI {
    private MessageManager messageManager;
    private UUID user;

    public InboxUI(MessageManager messageManager, UUID user) {
        this.messageManager = messageManager;
        this.user = user;
    }

    public void displayInbox() {
        System.out.println(messageManager.toString(user));
        //TODO: functionality for going back
        System.out.println("Press ENTER to go back");
    }

    public void recipientPrompt(int type) {
        // 1: specific attendee
        // 2: specific speaker
        switch (type) {
            case 1:
                System.out.println("Enter the username of the attendee you would like to send this message to.\n");
                break;
            case 2:
                System.out.println("Enter the username of the speaker you would like to send this message to.\n");
                break;
        }

    }

    public void bodyPrompt(int type) {
        // 1: message to specific person
        // 2: announcement to every attendee
        // 3: announcement to every attendee in a talk
        // 4: announcement to attendees in multiple talks
        // 5: announcement to every speaker
        switch (type) {
            case 1:
                System.out.print("Write your message: ");
                break;
            case 2:
                System.out.print("Make an announcement to all attendees: ");
                break;
            case 3:
                System.out.print("Make an announcement to the attendees of your talk: ");
                break;
            case 4:
                System.out.print("Make an announcement to the attendees of your chosen talks: ");
                break;
            case 5:
                System.out.print("Make an announcement to every speaker: ");
                break;
        }
    }

    public void sentPrompt() {
        //TODO: functionality for continuing
        System.out.println("Message sent! Press ENTER to continue.");
    }
}