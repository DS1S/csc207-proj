package Presenters;

import CoreEntities.Message;
import CoreEntities.Users.User;
import LoginSystem.UserManager;
import MessagingSystem.MessageManager;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InboxUI {
    private UserManager userManager;

    public InboxUI(UserManager userManager) {
        this.userManager = userManager;
    }

    public void displayInbox(List<Map<String, Object>> inboxData) {

        for(Map<String, Object> data : inboxData){
            StringBuilder sb = new StringBuilder();
            String recipient = userManager.getUsernameWithUUID((UUID) data.get("recipient"));
            String sender    = userManager.getUsernameWithUUID((UUID) data.get("sender"));
            data.remove("recipient");
            data.remove("sender");
            sb.append("From: " + sender + " To " + recipient + "\n");
            data.forEach((s, o) -> {
                sb.append(data.get(s).toString() + "\n");
            });
            System.out.println(sb);
            System.out.println("------------------------------------------------------");
        }

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