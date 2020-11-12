package MessagingSystem;
import LoginSystem.UserManager;
import coreUtil.IRunnable;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

public class MessageSystem implements IRunnable {
    private MessageManager messageManager;
    private UserManager userManager;

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

    @Override
    public String toString() {
        return "Messaging";
    }
}
