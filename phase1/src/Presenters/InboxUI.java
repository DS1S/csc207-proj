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

        if (inboxData.size() == 0) { System.out.println("Your inbox is empty! :("); }

        // TODO: functionality for this
        System.out.println("If you would like to send a message, press 1. If you want to go back, press 2.");
    }

    public void displayRecipientPrompt(int type) {
        if (type == 1) {
            System.out.println("Enter the username(s) of the attendee(s)/speaker(s) you would like to send this message to.\n");
        }
        if (type == 2) {
            System.out.println("Enter the names of the events for which you would like all attendees to receive this message.\n");
        }
    }

    public void displayBodyPrompt() {
        System.out.print("Write your message: ");
    }

    public void sentPrompt() {
        //TODO: functionality for continuing
        System.out.println("Message sent! Press ENTER to continue.");
    }
}