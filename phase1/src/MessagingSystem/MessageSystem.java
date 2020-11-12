package MessagingSystem;
import CoreEntities.Users.Perms;
import LoginSystem.UserManager;
import coreUtil.IRunnable;
import CoreEntities.FriendsList;
import CoreEntities.Users.User;
import CoreEntities.Event;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import java.util.List;
import SchedulingSystem.EventManager;

public class MessageSystem implements IRunnable {
    private MessageManager messageManager;
    private UserManager userManager;
    private final String NOT_A_FRIEND = "That user is not on your friends list!";
    private final String DO_NOT_HAVE_PERMISSION = "You do not have permission to do that!";

    public MessageSystem(MessageManager messageManager, UserManager userManager){
        this.messageManager = messageManager;
        this.userManager = userManager;
    }

    public void run() {
    }

    public void MessageAPerson(String receiver, String message) {
        messageManager.sendMessageToIndividual(userManager.getLoggedInUserUUID(),
                userManager.getUUIDWithUsername(receiver), message);
    }

    public void MessagePeople(List<String> recipients, String message) {
        ArrayList<UUID> recipientUUIDs = new ArrayList<>();
        for (String iter : recipients) {
            recipientUUIDs.add(userManager.getUUIDWithUsername(iter));
        }
        messageManager.sendMessageToMultiple(userManager.getLoggedInUserUUID(),
                recipientUUIDs, message);
    }

    public String MessageAttendees(List<String> events, String message) {
        if (!userManager.loggedInHasPermission(Perms.canMessageTalk)) {
            return DO_NOT_HAVE_PERMISSION;
        }

        List<Event> eventList = new ArrayList<>();
        List<UUID> attendeeUUIDs = new ArrayList<>();
        return "";

    }


}
