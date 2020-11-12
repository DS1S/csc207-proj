package MessagingSystem;
import SchedulingSystem.EventManager;
import CoreEntities.Users.Perms;
import LoginSystem.UserManager;
import coreUtil.IRunnable;
import Presenters.InboxUI;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
import java.util.Scanner;

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
        //this.inboxUI.displayInbox();
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
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
