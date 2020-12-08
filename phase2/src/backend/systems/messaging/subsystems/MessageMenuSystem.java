package backend.systems.messaging.subsystems;

import backend.entities.Statuses;
import backend.systems.MenuSystem;
import backend.entities.users.Perms;
import backend.systems.usermangement.managers.UserManager;
import backend.systems.messaging.managers.MessageManager;
import frontend.InboxUI;
import utility.inputprocessors.IndexProcessor;
import utility.inputprocessors.OptionIndexProcessor;

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
        this.inboxUI = new InboxUI(userManager);
    }

    /**
     * Processes an integer input in any messaging page.
     * @param option The input to be processed. 1 allows for displaying all read messages
     * to the logged in user, 2 allows for displaying all messages by a specific status,
     * 3 allows for sending a message, 4 allows for modifying message statuses, and 5 allows for
     * message deletion.
     */
    protected void processBaseInput(int option) {
        switch (option) {
            case(1):
                viewMessages(messageManager.getInboxData(userManager.getLoggedInUserUUID()), Statuses.READ);
                break;
            case(2):
                viewMessagesByStatus();
                break;
            case(3):
                processSendMessage();
                break;
            case(4):
                setMessageStates();
                break;
            case(5):
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
            if (!userManager.hasPermission(recipient, Perms.CAN_BE_MESSAGED) &&
                    !userManager.loggedInHasPermission(Perms.CAN_SCHEDULE)) {
                inboxUI.displayError("One or more recipients are not able to be messaged! " +
                                        "Please Try to send again.");
                recipientUUIDs.add(null);
                return recipientUUIDs;
            }
        }
        return recipientUUIDs;
    }

    private void viewMessages(List<Map<String, Object>> inboxData, Statuses status) {
        int index = selectMessage(inboxData);
        if (index != -1) {
            inboxUI.displayMessage(inboxData.get(index));
            messageManager.changeMessageState(userManager.getLoggedInUserUUID(), index, status);
        }
    }

    private void viewMessagesByStatus() {
        int index = processStatusInput();
        List<Statuses> status = Collections.singletonList(Statuses.values()[index]);
        Statuses targetStatus = messageManager.getStatusOverwrite(status.get(0));
        viewMessages(messageManager.getInboxData(userManager.getLoggedInUserUUID(), status), targetStatus);
    }

    private List<String> statusesToString(Statuses[] statuses) {
        List<String> statusNames = new ArrayList<>();
        for (Statuses status : statuses) {
            statusNames.add(status.toString());
        }
        return statusNames;
    }

    private int processStatusInput() {
        Statuses[] statuses = Statuses.values();
        List<String> optionNames = statusesToString(statuses);
        inboxUI.displayOptions(optionNames, false);
        IndexProcessor<Integer> optionProcessor = new OptionIndexProcessor(new Scanner(System.in), statuses.length);
        return optionProcessor.processInput() - 1;
    }

    private void setMessageStates() {
       int index = processStatusInput();
       List<Statuses> statuses = Arrays.asList(Statuses.values());
       viewMessages(messageManager.getInboxData(userManager.getLoggedInUserUUID(), statuses), Statuses.values()[index]);
       inboxUI.displayStatusChanged(Statuses.values()[index]);
    }

    private void processMessageDeletion() {
        List<Map<String, Object>> inboxData = messageManager.getInboxData(userManager.getLoggedInUserUUID());
        int index = selectMessage(inboxData);
        if (index != -1) {
            messageManager.deleteMessage(userManager.getLoggedInUserUUID(), index);
            inboxUI.displayMessageDeleted();
        }
    }

    private int selectMessage(List<Map<String, Object>> inboxData) {
        inboxUI.displayInbox(inboxData);
        if (inboxData.isEmpty()) {
            return -1;
        }
        IndexProcessor<Integer> optionProcessor = new OptionIndexProcessor(new Scanner(System.in), inboxData.size());
        return optionProcessor.processInput() - 1;
    }
}
