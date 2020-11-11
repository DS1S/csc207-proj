package MessagingSystem;
import CoreEntities.Message;
import LoginSystem.UserManager;
import coreUtil.IRunnable;

import java.time.LocalTime;
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
        Message m = new Message(UUID.randomUUID(), userManager.getLoggedInUser().getUUID(),
                userManager.getUUIDWithUsername(receiver), message, LocalTime.now());
        messageManager.sendMessageToIndividual(userManager.getUUIDWithUsername(receiver), m);
    }

    public void MessagePeople(List<String> recipients, String message) {
        ArrayList<Message> temp = new ArrayList<>();
    }
}
