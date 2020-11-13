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

/**
 * A messaging system with a message manager, a user manager, an event manager, a no permission string,
 * a cannot message organizer string, a string indicating that one intended recipient can not be messaged,
 * and an inbox UI.
 */
public class MessageSystem implements IRunnable {
    /** The message manager used by the system. */
    private MessageManager messageManager;

    /** The user manager used by the system. */
    private UserManager userManager;

    /** The event manager used by the system. */
    private EventManager eventManager;

    /** The string used to indicate that the user does not have permission to perform an action. */
    private final String NO_PERMISSION = "You do not have permission to do that!";

    /** The string used to indicate that the user can not message an organizer. */
    private final String CANNOT_MESSAGE_ORGANIZER = "You can not message an organizer!";

    /** The string used to indicate that one of the user's intended recipients is an organizer who can
     * not be messaged. */
    private final String ONE_IS_AN_ORGANIZER = "At least one of your recipients is an organizer. You can not message" +
            "an organizer!";

    /** The inbox UI used by the system. */
    private InboxUI inboxUI;

    /**
     * Constructs a new messaging system with the information below.
     * @param messageManager the message manager used by the system
     * @param userManager the user managed used by the system
     * @param eventManager the event manager used by the system
     */
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

    /**
     * Sends a message to a user with the information below if the recipient is not an organizer.
     * @param receiver username of the intended recipient
     * @param message body of the message
     * @return a no permission string if the intended recipient is an organizer, else empty string
     */
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

    /**
     * Sends a message to a list of users with the information below if none of the recipients is an organizer.
     * @param recipients list of usernames of the intended recipients
     * @param message body of the message
     * @return a string indicating that one recipient is an organizer if one of the recipients is an
     * organizer, else empty string
     */
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

    /**
     * Allows a speaker to send a message to all attendees of given event(s).
     * @param events list of event names for which attendees will receive the message
     * @param msg body of the message
     * @return a string indicating that the user does not have permission to send the message if they are
     * not a speaker, else empty string
     */
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

    /**
     * An override of the built-in toString method.
     * @return the string "Messaging"
     */
    @Override
    public String toString(){
        return "Messaging";
    }
}
