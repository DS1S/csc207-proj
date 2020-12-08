package backend.systems.admin;

import backend.entities.Statuses;
import backend.systems.MenuSystem;
import backend.systems.social.managers.MessageManager;
import backend.systems.usermangement.managers.UserManager;
import frontend.AdminUI;
import frontend.InboxUI;
import utility.inputprocessors.InputProcessor;
import utility.inputprocessors.OptionInputProcessor;

import java.util.*;

/**
 * An extension of MenuSystem that displays an Admin their individual and administrative messaging
 * options and processes their input.
 */
class AdminMessageViewerSystem extends MenuSystem {

    UserManager um;
    private AdminUI adminUI;
    private InboxUI inboxUI;
    private final Scanner scanner = new Scanner(System.in);
    private MessageManager messageManager;

    /**
     * Constructs a new AdminMessageViewerSystem with the given information.
     * @param um The UserManager that will be used by the AdminSystem
     * @param messageManager The MessageManager that will be used by the AdminSystem
     */
    public AdminMessageViewerSystem(UserManager um, MessageManager messageManager){
        super(4);
        adminUI = new AdminUI();
        inboxUI = new InboxUI(um);
        this.um = um;
        this.messageManager = messageManager;
    }

    /**
     * Displays message managing options for the user to choose.
     */
    @Override
    protected void displayOptions() {
        adminUI.displayMessageViewOptions();
    }

    /**
     * Processes input for the selection of a message managing option.
     * @param index the index of the option chosen.
     */
    @Override
    protected void processInput(int index) {
        adminUI.promptUserName();
        String username = scanner.nextLine();
        if (um.getUUIDWithUsername(username) == null){
            adminUI.displayInvalidUser();
            return;
        }

        switch(index){
            case 1:
                inboxUI.displayInbox(messageManager.getSentMessageData(um.getUUIDWithUsername(username)));
                break;
            case 2:
                inboxUI.displayInbox(messageManager.getInboxData(um.getUUIDWithUsername(username)));
                break;
            case 3:
                inboxUI.displayInbox(messageManager.getInboxData(um.getUUIDWithUsername(username)));

                if (!messageManager.userHasMail(um.getUUIDWithUsername(username))) {
                    return;
                }

                List<Map<String, Object>> inboxData = messageManager.getInboxData(um.getUUIDWithUsername(username),
                        Arrays.asList(Statuses.values()));
                inboxUI.displayInbox(inboxData);
                InputProcessor<Integer> optionProcessor = new OptionInputProcessor(new Scanner(System.in),
                        inboxData.size());
                int msgNumber = optionProcessor.processInput() - 1;
                messageManager.deleteMessage(um.getUUIDWithUsername(username), msgNumber);
                break;
        }
    }
}
