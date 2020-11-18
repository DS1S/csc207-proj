package MessagingSystem.SubSystems;

import LoginSystem.UserManager;
import MessagingSystem.MessageManager;

/**
 * A BaseMessageSubSystem that extends MessageSubSystem.
 */
public class BaseMessageSubSystem extends MessageSubSystem {

    /**
     * Constructs a new instance of BaseMessageSubSystem using the given parameters.
     * @param userManager the user manager used by the system
     * @param messageManager the message manager used by the system
     * @param numOptions the number of options available to the user on the menu
     */
    public BaseMessageSubSystem(UserManager userManager, MessageManager messageManager, int numOptions) {
        super(userManager, messageManager, numOptions);
    }

    @Override
    protected void displayOptions() {
        inboxUI.displayAttendingMenuOptions();
    }

    @Override
    protected void processMainSignInput(int index) {
        processBaseInput(index);
    }
}
