package Presenters;

import CoreEntities.Message;
import CoreEntities.Users.User;
import LoginSystem.UserManager;
import MessagingSystem.MessageManager;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InboxUI extends ErrorUI {
    private UserManager userManager;

    public InboxUI(UserManager userManager) {
        this.userManager = userManager;
    }

    public void displayInbox(List<Map<String, Object>> inboxData) {

        int i = 1;
        for(Map<String, Object> data : inboxData){
            StringBuilder sb = new StringBuilder();
            String recipient = userManager.getUsernameWithUUID((UUID) data.get("recipient"));
            String sender    = userManager.getUsernameWithUUID((UUID) data.get("sender"));
            data.remove("recipient");
            data.remove("sender");
            System.out.println("-------------------------------------------------------");
            System.out.println("Message " + i);
            sb.append("From: " + sender + " To " + recipient + "\n");
            data.forEach((s, o) -> {
                sb.append(data.get(s).toString() + "\n");
            });
            data.put("recipient", userManager.getUUIDWithUsername(recipient));
            data.put("sender", userManager.getUUIDWithUsername(sender));
            System.out.println(sb);
            System.out.println("------------------------------------------------------");
        }

        if (inboxData.size() == 0) { System.out.println("Your inbox is empty! :("); }
    }

    public void displayAttendingMenuOptions(){
        displayBaseInboxOptions();
        System.out.println("2. Send Message.");
        displayExitOption(3);
    }

    public void displayTalkSpeakerMenuOptions(){
        displayBaseInboxOptions();
        System.out.println("2. Reply to Attendee.");
        System.out.println("3. Message attendees of your talk(s).");
        displayExitOption(4);
    }

    public void displaySchedulerMenuOptions(){
        displayBaseInboxOptions();
        System.out.println("2. Send Message.");
        System.out.println("3. Message all attendees.");
        System.out.println("4. Message all speakers.");
        displayExitOption(5);
    }

    public void displayBodyPrompt() {
        System.out.print("Write your message: ");
    }

    public void displayUserPrompt() {
        System.out.print("Enter Recipient(s), comma separated:");
    }

    public void sentPrompt() {
        System.out.println("Message sent!");
    }

    public void talksPrompt() {System.out.println("Enter Talks: ");}

    private void displayBaseInboxOptions(){
        System.out.println("What would you like to do?\n");
        System.out.println("1. View Inbox.");
    }

    private void displayExitOption(int index){
        System.out.println(index + ". Return to main Menu");
    }



}