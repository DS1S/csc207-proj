package backend.systems.social;

import backend.systems.usermangement.managers.UserManager;
import backend.systems.social.managers.MessageManager;

/**
 * An extension of MessageMenuSystem responsible for the message menu available to attendees.
 */
class RegularMessageMenuSystem extends MessageMenuSystem {
    /**
     * Constructs a new instance of RegularMessageMenuSystem using the given parameters.
     * @param userManager The user manager used by the system.
     * @param messageManager The message manager used by the system.
     */
    public RegularMessageMenuSystem(UserManager userManager, MessageManager messageManager) {
        super(userManager, messageManager, 6);
    }

    /**
     * Displays the options in the messaging page.
     */
    @Override
    protected void displayOptions() {
        inboxUI.displayAttendingMenuOptions();
    }

    /**
     * Processes an integer input in the messaging page.
     * @param index The input to be processed.
     */
    @Override
    protected void processInput(int index) {
        processBaseInput(index);
    }
}
