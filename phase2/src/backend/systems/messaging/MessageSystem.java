package backend.systems.messaging;
import backend.systems.SubSystem;
import backend.systems.messaging.subsystems.*;
import backend.systems.messaging.managers.MessageManager;
import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;
import backend.systems.RunnableSystem;
import frontend.InboxUI;
import static backend.coreentities.users.PERMS.*;

/**
 * A messaging system with a message manager, a user manager, an event manager, and an inbox UI.
 */
public class MessageSystem implements RunnableSystem {
    private final MessageManager messageManager;
    private final UserManager userManager;
    private final EventManager eventManager;
    private final InboxUI inboxUI;

    /**
     * Constructs a new messaging system with the information below.
     * @param messageManager The message manager used by the system.
     * @param userManager The user managed used by the system.
     * @param eventManager The event manager used by the system.
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

        //TODO: Refactor so it behaves as a subsystem - I will worry about it no one else has to worry abt imp.

        MessageSubSystem subsystem = null;
        MessageSubSystemFactory messageSubSystemFactory = new MessageSubSystemFactory();

        if (userManager.loggedInHasPermission(canSchedule)) {
            subsystem = messageSubSystemFactory.createMessageSubSystem("organizer",
                    userManager, messageManager, 5, eventManager);
        }
        else if (userManager.loggedInHasPermission(canSpeakAtTalk)) {
            subsystem = messageSubSystemFactory.createMessageSubSystem("speaker", userManager,
                    messageManager, 4, eventManager);
        }

        //allocate a default message subsystem
        if(subsystem == null) subsystem = messageSubSystemFactory.createMessageSubSystem("regular",
                userManager, messageManager, 3, eventManager);
        subsystem.run();
    }

    /**
     * An override of the built-in toString method.
     * @return The string "Messaging".
     */
    @Override
    public String toString() {
        return "Messaging";
    }
}
