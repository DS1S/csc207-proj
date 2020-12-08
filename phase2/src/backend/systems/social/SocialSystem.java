package backend.systems.social;
import backend.systems.MenuSystem;
import backend.systems.social.managers.MessageManager;
import backend.systems.events.managers.EventManager;
import backend.systems.usermangement.managers.UserManager;
import frontend.MenuUI;
import utility.RunnableSystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static backend.entities.users.Perms.*;

/**
 * A messaging system with a message manager, a user manager, an event manager, and an inbox UI.
 */
public class SocialSystem extends MenuSystem {
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
    public SocialSystem(MessageManager messageManager, UserManager userManager,
                        List<EventManager> eventManagers) {
        this.messageManager = messageManager;
        this.userManager = userManager;
        this.eventManagers = eventManagers;
        this.subSystems = new HashMap<>();
        this.menuUI = new MenuUI();
        initializeSubSystems();
        changeNumOptions(subSystems.size() + 1);
    }

    /**
     * Calls the message subsystem factory to initialize the message subsystems.
     */
    public void initializeSubSystems() {
        SocialSubSystemsFactory socialSubSystemsFactory = new SocialSubSystemsFactory();

        if (userManager.loggedInHasPermission(CAN_SCHEDULE)) {
            subSystems.put(subSystems.size() + 1, socialSubSystemsFactory.createMessageSubSystem("organizer",
                    userManager, messageManager, eventManagers));
        }
        else if (userManager.loggedInHasPermission(CAN_SPEAK_AT_TALK)) {
            subSystems.put(subSystems.size() + 1, socialSubSystemsFactory.createMessageSubSystem("speaker", userManager,
                    messageManager, eventManagers));
        }

        // Allocate a default message subsystem
        if (subSystems.size() == 0) subSystems.put(subSystems.size() + 1,
                socialSubSystemsFactory.createMessageSubSystem("regular",
                        userManager, messageManager, eventManagers));

        subSystems.put(subSystems.size() + 1,
                socialSubSystemsFactory.createMessageSubSystem("linker", userManager, messageManager
                        , eventManagers));
    }

    /**
     * Overrides the displayOptions method.
     */
    @Override
    protected void displayOptions() {
        menuUI.displayOptions(convertSubSystemsToNames(subSystems), true, true);
    }

    /**
     * Overrides the processInput method.
     * @param index The input to be processed.
     */
    @Override
    protected void processInput(int index) {
        if (subSystems.containsKey(index)) {
            subSystems.get(index).run();
        }
    }

    /**
     * An override of the built-in toString method.
     * @return The name of the system.
     */
    @Override
    public String toString() {
        return "Social";
    }
}
