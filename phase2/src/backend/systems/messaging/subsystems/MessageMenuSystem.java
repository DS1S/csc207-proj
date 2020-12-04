package backend.systems.messaging.subsystems;

import backend.systems.MenuSystem;
import backend.entities.users.PERMS;
import backend.systems.usermangement.managers.UserManager;
import backend.systems.messaging.managers.MessageManager;
import frontend.InboxUI;

import java.util.*;

/**
 * A MessageMenuSystem abstract class that is extended by other Message subsystems
 */
public abstract class MessageMenuSystem extends MenuSystem {
    protected UserManager userManager;
    protected MessageManager messageManager;
    protected InboxUI inboxUI;

    /**
     * Creates an object of MessageMenuSystem
     * @param userManager A UserManager object that is already instantiated at the point this is instantiated.
     * @param messageManager A MessageManager object that is already instantiated at the point this is instantiated.
     * @param numOptions The number of options in the menu.
     */
    public MessageMenuSystem(UserManager userManager, MessageManager messageManager, int numOptions) {
        super(numOptions);
        this.userManager = userManager;
        this.messageManager = messageManager;
        this.inboxUI = new InboxUI(userManager, messageManager);
    }

    /**
     * Processes an integer input in any messaging page.
     * @param option The input to be processed. 1 allows for displaying all messages to the logged in user.
     *               2 allows for sending a message.
     */
    protected void processBaseInput(int option) {
        switch (option) {
            case(1):
                inboxUI.displayInbox(messageManager.getInboxData(userManager.getLoggedInUserUUID()));
                break;
            case(2):
                processSendMessage();
                break;
            case(5):
                processMessageViewing();
                inboxUI.displayMessage();
                messageManager.setToDisplay("");
                break;
            case(6):
            case(7):
            case(8):
                processMessageDeletion();
                break;
        }
    }

    /**
     * Processes a message body when the user composes a message.
     * @return The user's input.
     */
    protected String processMessageBody() {
        inboxUI.displayBodyPrompt();
        return askForString("Message");
    }

    /**
     * Processes a title when the uses composes a message.
     * @return The user's input.
     */
    protected String processTitle() {
        inboxUI.displayTitlePrompt();
        return askForString("Title");
    }

    private void processSendMessage() {
        List<UUID> recipients;
        do {
            inboxUI.displayUserPrompt();
            recipients = askForUsernames();
        } while (recipients.contains(null));

        String title = processTitle();
        String message = processMessageBody();
        messageManager.sendMessageToMultiple(userManager.getLoggedInUserUUID(), recipients, message, title);
        inboxUI.sentPrompt();
    }

    private void processMessageDeletion() {
        inboxUI.deletionPrompt();
        String title = askForString("Title");
        messageManager.deleteMessage(messageManager.getMessageIdByTitle(title,
                messageManager.getInboxByUserId(userManager.getLoggedInUserUUID())),
                messageManager.getInboxByUserId(userManager.getLoggedInUserUUID()));
        inboxUI.deletedPrompt();
    }

    private void processMessageViewing() {
        inboxUI.viewMessagePrompt();
        String title = askForString("Title");
        UUID userId = userManager.getLoggedInUserUUID();
        messageManager.setRead(userId, messageManager.getMessageIdByTitle(title,
                        messageManager.getInboxByUserId(userId)));
        String body = messageManager.getBodyByTitle(title, messageManager.getInboxByUserId(userId));
        messageManager.setToDisplay(body);
    }

    private List<UUID> askForUsernames() {
        String usernames = askForString("User(s)");
        String[] recipients = usernames.split(",");
        ArrayList<UUID> recipientUUIDs = new ArrayList<>();
        for (String recipient : recipients) {
            recipientUUIDs.add(userManager.getUUIDWithUsername(recipient));
        }

        if (recipientUUIDs.contains(null)) {
            inboxUI.displayError("A username you entered does not exist.");
            return  recipientUUIDs;
        }

        for (UUID recipient : recipientUUIDs) {
            if (!userManager.hasPermission(recipient, PERMS.canBeMessaged)) {
                inboxUI.displayError("One or more recipients are not able to be messaged! " +
                                        "Please Try to send again.");
                recipientUUIDs.add(null);
                return recipientUUIDs;
            }
        }
        return recipientUUIDs;
    }

}
