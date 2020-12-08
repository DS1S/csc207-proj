package backend.systems.messaging.subsystems;

import backend.systems.usermangement.managers.UserManager;
import backend.systems.messaging.managers.MessageManager;

/**
 * An extension of MessageMenuSystem responsible for the message menu available to attendees.
 */
class RegularMessageMenuSystem extends MessageMenuSystem {
    /**
     * Constructs a new instance of RegularMessageMenuSystem using the given parameters.
     * @param userManager The user manager used by the system.
     * @param messageManager The message manager used by the system.
     * @param numOptions The number of options available to the user on the menu.
     */
    public RegularMessageMenuSystem(UserManager userManager, MessageManager messageManager, int numOptions) {
        super(userManager, messageManager, numOptions);
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

    /**
     * Returns the string representation of this system.
     * @return The string "Inbox/Messaging".
     */
    @Override
    public String toString() { return "Inbox/Messaging"; }
}
