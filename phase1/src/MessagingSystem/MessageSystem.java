package MessagingSystem;
import EventSystem.Managers.EventManager;
import LoginSystem.UserManager;
import Main.SubSystem;
import MessagingSystem.SubSystems.AdvancedMessageSubSystem;
import MessagingSystem.SubSystems.BaseMessageSubSystem;
import MessagingSystem.SubSystems.SpeakerMessageSubSystem;
import coreUtil.IRunnable;
import Presenters.InboxUI;
import static CoreEntities.Users.PERMS.*;

/**
 * A messaging system with a message manager, a user manager, an event manager, and an inbox UI.
 */
public class MessageSystem implements IRunnable {
    private final MessageManager messageManager;
    private final UserManager userManager;
    private final EventManager eventManager;
    private final InboxUI inboxUI;

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
        this.inboxUI = new InboxUI(userManager);
    }

    /**
     * Implements the run method from the IRunnable interface in order to run this System.
     */
    @Override
    public void run() {
        SubSystem subsystem = null;

        if (userManager.loggedInHasPermission(canSchedule)) {
            subsystem = new AdvancedMessageSubSystem(userManager, messageManager, 5);
        }
        else if (userManager.loggedInHasPermission(canSpeakAtTalk)) {
            subsystem = new SpeakerMessageSubSystem(userManager, messageManager, 4, eventManager);
        }

        //allocate a default message system
        if(subsystem == null) subsystem = new BaseMessageSubSystem(userManager, messageManager, 3);
        subsystem.run();
    }

    /**
     * An override of the built-in toString method.
     * @return the string "Messaging"
     */
    @Override
    public String toString() {
        return "Messaging";
    }
}
