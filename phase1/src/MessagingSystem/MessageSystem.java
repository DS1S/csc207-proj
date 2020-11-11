package MessagingSystem;
import LoginSystem.UserManager;
import coreController.IRunnable;
import java.util.UUID;

public class MessageSystem implements IRunnable {
    private MessageManager messageManager;
    private UserManager userManager;

    public MessageSystem(MessageManager messageManager, UserManager userManager){
        this.messageManager = messageManager;
        this.userManager = userManager;
    }

    public void run() {
    }

    // Methods to do with organizer as sender
    public void OrganizerMessageAPerson(String receiver, String message) {


    }

    // Methods to do with attendee as sender

    // Methods to do with speaker as sender


}
