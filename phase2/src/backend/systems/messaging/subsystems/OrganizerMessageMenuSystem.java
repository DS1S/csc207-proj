package backend.systems.messaging.subsystems;

import backend.entities.users.PERMS;
import backend.systems.usermangement.managers.UserManager;
import backend.systems.messaging.managers.MessageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * An AdvancedMessageSubSystem that extends MessageMenuSystem.
 */
class OrganizerMessageMenuSystem extends MessageMenuSystem {
    /**
     * Constructs a new instance of AdvancedMessageSubSystem using the given parameters.
     * @param userManager The user manager used by the system.
     * @param messageManager The message manager used by the system.
     * @param numOptions The number of options available to the user on the menu.
     */
    public OrganizerMessageMenuSystem(UserManager userManager, MessageManager messageManager, int numOptions) {
        super(userManager, messageManager, numOptions);
    }

    /**
     * Tells the inbox UI to display options for organizers.
     */
    @Override
    protected void displayOptions() {
        inboxUI.displaySchedulerMenuOptions();
    }

    /**
     * Processes an integer input in the organizer's messaging subsystem.
     * @param index The input to be processed. 3 allows for messaging all attendees. 4 allows
     *              for messaging all speakers.
     */
    @Override
    protected void processInput(int index) {
        processBaseInput(index);
        switch (index) {
            case(6):
                processMessageAllAttendees();
                break;
            case(7):
                processMessageAllSpeakers();
                break;
        }
    }

    private void processMessageAllAttendees() {
        String message = processMessageBody();
        String title = processTitle();
        messageUserWithPerm(message, PERMS.canSpeakAtTalk, title);
    }

    private void processMessageAllSpeakers() {
        String message = processMessageBody();
        String title = processTitle();
        messageUserWithPerm(message, PERMS.canSignUpEvent, title);
    }

    private void messageUserWithPerm(String message, PERMS perm, String title) {
        List<UUID> userUUIDs = userManager.getUUIDs();
        List<UUID> targetUUIDs = new ArrayList<>();
        for (UUID id : userUUIDs) {
            if (userManager.hasPermission(id, perm)) {
                targetUUIDs.add(id);
            }
        }
        messageManager.sendMessageToMultiple(userManager.getLoggedInUserUUID(), targetUUIDs, message, title);
        inboxUI.sentPrompt();
    }
}
