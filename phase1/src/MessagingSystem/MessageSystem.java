package MessagingSystem;
import SchedulingSystem.EventManager;
import CoreEntities.Users.Perms;
import LoginSystem.UserManager;
import coreUtil.IRunnable;
import Presenters.InboxUI;

import java.util.*;

public class MessageSystem implements IRunnable {
    private MessageManager messageManager;
    private UserManager userManager;
    private EventManager eventManager;
    private final String NO_PERMISSION = "You do not have permission to do that!";
    private final String CANNOT_MESSAGE_ORGANIZER = "You can not message an organizer!";
    private final String ONE_IS_AN_ORGANIZER = "At least one of your recipients is an organizer. You can not message" +
            "an organizer!";
    private InboxUI inboxUI;

    public MessageSystem(MessageManager messageManager, UserManager userManager,
                         EventManager eventManager) {
        this.messageManager = messageManager;
        this.userManager = userManager;
        this.eventManager = eventManager;
    }

    @Override
    public void run() {
        UUID t = this.userManager.getLoggedInUserUUID();
        this.inboxUI.displayInbox(this.messageManager.getInboxData(t));

        // TODO: reply to message prompt here

        ArrayList<Integer> allowedOptions = new ArrayList<>();
        this.inboxUI.displayRecipientPrompt(1);
        allowedOptions.add(1);
        if (this.userManager.loggedInHasPermission(Perms.canMessageTalk)) {
            this.inboxUI.displayRecipientPrompt(2);
            allowedOptions.add(2);
        }

        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        if (option == 1) {
            String usernames = scanner.nextLine();
            this.inboxUI.displayBodyPrompt();
            String message = scanner.nextLine();
            String e = this.MessagePeople(Arrays.asList(usernames.split(",")), message);
            // TODO: handle error here
        } else if (allowedOptions.contains(option) && option == 2) {
            String events = scanner.nextLine();
            this.inboxUI.displayBodyPrompt();
            String message = scanner.nextLine();
            String e = this.SpeakerMessageAttendees(Arrays.asList(events.split(",")), message);
            // TODO: handle error here
        } else {
            // TODO: invalid option error
        }
    }

    public String MessageAPerson(String receiver, String message) {
        if (!userManager.hasPermission(receiver, Perms.canBeMessaged)){
            return CANNOT_MESSAGE_ORGANIZER;
        }
        else {
            messageManager.sendMessageToIndividual(userManager.getLoggedInUserUUID(),
                    userManager.getUUIDWithUsername(receiver), message);
            return "";
        }
    }

    public String MessagePeople(List<String> recipients, String message) {
        for (String recipient : recipients){
            if (!userManager.hasPermission(recipient, Perms.canBeMessaged)){
                return ONE_IS_AN_ORGANIZER;
            }
        }
        ArrayList<UUID> recipientUUIDs = new ArrayList<>();
        for (String iter : recipients) {
            recipientUUIDs.add(userManager.getUUIDWithUsername(iter));
        }
        messageManager.sendMessageToMultiple(userManager.getLoggedInUserUUID(),
                recipientUUIDs, message);
        return "";
    }

    public String SpeakerMessageAttendees(List<String> events, String msg) {
        if (!userManager.loggedInHasPermission(Perms.canMessageTalk)) {
            return NO_PERMISSION;
        } else {
            List<UUID> attendeeUUIDs = new ArrayList<>();
            for (String event : events) {
                List<UUID> eventUUIDs = eventManager.retrieveAttendees(event, userManager.getLoggedInUserUUID());
                attendeeUUIDs.addAll(eventUUIDs);
            }
            messageManager.sendMessageToMultiple(userManager.getLoggedInUserUUID(), attendeeUUIDs, msg);
            return "";
        }
    }

    @Override
    public String toString(){
        return "Messaging";
    }
}
