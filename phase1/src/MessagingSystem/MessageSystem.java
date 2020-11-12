package MessagingSystem;
import SchedulingSystem.EventManager;
import CoreEntities.Users.Perms;
import CoreEntities.Users.User;
import CoreEntities.FriendsList;
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
    private InboxUI inboxUI;

    public MessageSystem(MessageManager messageManager, UserManager userManager,
                         EventManager eventManager) {
        this.messageManager = messageManager;
        this.userManager = userManager;
        this.eventManager = eventManager;
    }

    @Override
    public void run() {
        this.inboxUI.displayInbox();
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
    }

    /* The block of code below that has been commented out prevents users from messaging users not on their
    friends list. It is not needed for phase 1, and is being kept in case it is needed for phase 2.
     */
    public String MessageAPerson(String receiver, String message) {
        /*UUID receiverId = userManager.getUUIDWithUsername(receiver);
        UUID senderId = userManager.getLoggedInUserUUID();
        User sender = userManager.getUserWithUUID(senderId);
        FriendsList friendList = friendManager.getFriendsList(sender);
        if (!friendList.isFriend(sender, receiverId)) {
            return NOT_A_FRIEND;
        } else {*/
        messageManager.sendMessageToIndividual(userManager.getLoggedInUserUUID(),
                userManager.getUUIDWithUsername(receiver), message);
            return "";
        }

        /* The block of code below that has been commented out prevents users from messaging users not on their
        friends list. It is not needed for phase 1, and is being kept in case it is needed for phase 2.
         */
    public String MessagePeople(List<String> recipients, String message) {
        ArrayList<UUID> recipientUUIDs = new ArrayList<>();
        for (String iter : recipients) {
            recipientUUIDs.add(userManager.getUUIDWithUsername(iter));
        }
        /*UUID senderId = userManager.getLoggedInUserUUID();
        User sender = userManager.getUserWithUUID(senderId);
        FriendsList friendList = friendManager.getFriendsList(sender);
        for (UUID recipientUUID : recipientUUIDs){
            if (!friendList.isFriend(sender, recipientUUID)){
                return ONE_NOT_FRIEND;
            }
        }*/
        messageManager.sendMessageToMultiple(userManager.getLoggedInUserUUID(),
                recipientUUIDs, message);
        return "";
    }

    public String MessageAttendees(List<String> events, String msg) {
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

        @Override
        public String toString;(); {
            return "Messaging";
        }
    }
}
