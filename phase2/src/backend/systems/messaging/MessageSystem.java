package backend.systems.messaging;
import backend.entities.users.User;
import backend.systems.MenuSystem;
import backend.systems.messaging.subsystems.*;
import backend.systems.messaging.managers.MessageManager;
import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;
import frontend.MenuUI;
import utility.RunnableSystem;
import frontend.InboxUI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static backend.entities.users.PERMS.*;

/**
 * A messaging system with a message manager, a user manager, an event manager, and an inbox UI.
 */
public class MessageSystem extends MenuSystem {
    private final MessageManager messageManager;
    private final UserManager userManager;
    private final List<EventManager> eventManagers;
    private final Map<Integer, RunnableSystem> subSystems;
    private final MenuUI menuUI;

    /**
     * Constructs a new messaging system with the information below.
     * @param messageManager The message manager used by the system.
     * @param userManager The user managed used by the system.
     * @param eventManagers The list of event managers used by the system.
     */
    public MessageSystem(MessageManager messageManager, UserManager userManager,
                         List<EventManager> eventManagers) {
        this.messageManager = messageManager;
        this.userManager = userManager;
        this.eventManagers = eventManagers;
        this.subSystems = new HashMap<>();
        this.menuUI = new MenuUI();
        initializeSubSystems();
        changeNumOptions(subSystems.size() + 1);
    }

    public void initializeSubSystems() {
        MessageSubSystemFactory messageSubSystemFactory = new MessageSubSystemFactory();

        if (userManager.loggedInHasPermission(canSchedule)) {
            subSystems.put(subSystems.size() + 1, messageSubSystemFactory.createMessageSubSystem("organizer",
                    userManager, messageManager, 5, eventManagers));
        }
        else if (userManager.loggedInHasPermission(canSpeakAtTalk)) {
            subSystems.put(subSystems.size() + 1, messageSubSystemFactory.createMessageSubSystem("speaker", userManager,
                    messageManager, 4, eventManagers));
        }

        // Allocate a default message subsystem
        if (subSystems.size() == 0) subSystems.put(subSystems.size() + 1,
                messageSubSystemFactory.createMessageSubSystem("regular",
                        userManager, messageManager, 3, eventManagers));

        subSystems.put(subSystems.size() + 1,
                messageSubSystemFactory.createMessageSubSystem("linker", userManager, messageManager,
                        4, eventManagers));
    }

    @Override
    protected void displayOptions() {
        menuUI.displayOptions(convertSubSystemsToNames(subSystems), true);
    }

    @Override
    protected void processInput(int index) {
        if (subSystems.containsKey(index)) {
            subSystems.get(index).run();
        }
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
