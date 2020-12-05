package backend.systems.messaging.managers;

import backend.entities.Statuses;
import backend.entities.Message;

import java.io.Serializable;
import java.util.*;
import java.time.LocalTime;

/**
 * Class to manage messages.*/
public class MessageManager implements Serializable {
    private final Map<UUID, List<Message>> inboxes;

    /** Constructs a new message manager with the information below.
     * @param userIDs A list of user UUIDs.
     */
    public MessageManager(List<UUID> userIDs) {
        inboxes = new HashMap<>();
        for (UUID id : userIDs) {
            inboxes.put(id, new ArrayList<>());
        }
    }

    /**
     * Send a message to recipient by appending it to his list of messages.
     * @param sender The UUID of the sender.
     * @param recipient The UUID of the recipient.
     * @param msg A string which is the body of the message.
     */
    public void sendMessageToIndividual(UUID sender, UUID recipient, String msg, String title) {
        Message m = new Message(UUID.randomUUID(), sender, recipient, msg, LocalTime.now(), title);
        inboxes.get(recipient).add(m);
    }

    /**
     * Convenience method to send a message to multiple people at once.
     * @param sender The UUID of the sender.
     * @param recipients A list of UUIDs of the recipients
     * @param msg A string which is the body of the message.
     */
    public void sendMessageToMultiple(UUID sender, List<UUID> recipients, String msg, String title) {
        for (UUID recipient : recipients) {
            sendMessageToIndividual(sender, recipient, msg, title);
        }
    }

    /**
     * Returns true if a user has an inbox in inboxes, false otherwise.
     * @param userID The UUID of the user.
     * @return True if user has an inbox, false otherwise.
     */
    public boolean userHasInbox(UUID userID) {
        return inboxes.containsKey(userID);
    }

    public boolean userHasMail(UUID userID) {
        return userHasInbox(userID) && (!inboxes.get(userID).isEmpty());
    }

    /**
     * Maps the given UUID to an empty list in inboxes.
     * @param userID The UUID of the user.
     */
    public void addBlankInbox(UUID userID) {
        inboxes.put(userID, new ArrayList<>());
    }

    /**
     * A method to get all a user's messages and parse them into a generic format for the caller.
     * @param userID the UUID of the user whose messages are desired
     * @return A list of message representations (maps) with all message info
     */
    public List<Map<String, Object>> getInboxData(UUID userID) {
        List<Message> inbox = inboxes.get(userID);
        List<Map<String, Object>> inboxData = new ArrayList<>();

        for (Message message : inbox) {
            if (message.getStatus().equals(Statuses.READ) || message.getStatus().equals(Statuses.UNREAD)) {
                inboxData.add(message.extractData());
            }
        }

        return inboxData;
    }

    public List<Map<String, Object>> getInboxData(UUID userID, List<Statuses> statuses) {
        List<Message> inbox = inboxes.get(userID);
        List<Map<String, Object>> inboxData = new ArrayList<>();

        for (Message message : inbox) {
            if (statuses.contains(message.getStatus())) {
                inboxData.add(message.extractData());
            }
        }
        return inboxData;
    }

    /**
     * A method to get all a user's sent messages and parse them into a generic format for the caller.
     * @param userID the UUID of the user whose sent messages are desired
     * @return A list of message representations (maps) with all message info
     */
    public List<Map<String, Object>> getSentMessageData(UUID userID) {
        List<Map<String, Object>> inboxData = new ArrayList<>();
        for (List<Message> inbox : inboxes.values()) {
            for (Message msg : inbox) {
                if (msg.getSender() == userID) {
                    inboxData.add(msg.extractData());
                }
            }
        }
        return inboxData;
    }

    public void deleteMessage(UUID userID, int index) {
        inboxes.get(userID).remove(index);
    }

    public void changeMessageState(UUID userID, int index, Statuses status) {
        inboxes.get(userID).get(index).setStatus(status);
    }

    public Statuses getStatusOverwrite(Statuses status) {
        if (!status.isOverReadable()) {
            return status;
        }
        return Statuses.READ;
    }
}
