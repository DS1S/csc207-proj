package backend.systems.social;

import backend.entities.users.Perms;
import backend.systems.usermangement.managers.UserManager;
import backend.systems.social.managers.MessageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * An extension of MessageMenuSystem responsible for the message menu available to organizers.
 */
class OrganizerMessageMenuSystem extends MessageMenuSystem {

    /**
     * Constructs a new instance of OrganizerMessageMenuSystem using the given parameters.
     * @param userManager The user manager used by the system.
     * @param messageManager The message manager used by the system.
     */
    public OrganizerMessageMenuSystem(UserManager userManager, MessageManager messageManager) {
        super(userManager, messageManager, 8);
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
        messageUserWithPerm(message, Perms.CAN_SPEAK_AT_TALK, title);
    }

    private void processMessageAllSpeakers() {
        String message = processMessageBody();
        String title = processTitle();
        messageUserWithPerm(message, Perms.CAN_SIGN_UP_EVENT, title);
    }

    private void messageUserWithPerm(String message, Perms perm, String title) {
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
