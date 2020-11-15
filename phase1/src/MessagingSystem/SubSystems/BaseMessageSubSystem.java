package MessagingSystem.SubSystems;

import LoginSystem.UserManager;
import MessagingSystem.MessageManager;

public class BaseMessageSubSystem extends MessageSubSystem {

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
