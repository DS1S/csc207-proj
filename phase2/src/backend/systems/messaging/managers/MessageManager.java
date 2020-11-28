package backend.systems.messaging.managers;
import backend.coreentities.Message;

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
    public void sendMessageToIndividual(UUID sender, UUID recipient, String msg) {
        Message m = new Message(UUID.randomUUID(), sender, recipient, msg, LocalTime.now());
        inboxes.get(recipient).add(m);
    }


    /**
     * Convenience method to send a message to multiple people at once.
     * @param sender The UUID of the sender.
     * @param recipients A list of UUIDs of the recipients
     * @param msg A string which is the body of the message.
     */
    public void sendMessageToMultiple(UUID sender, List<UUID> recipients, String msg) {
        for (UUID recipient : recipients) {
            sendMessageToIndividual(sender, recipient, msg);
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
            inboxData.add(message.extractData());
        }

        return inboxData;
    }


    /*
    Potential use in phase 2

    public String toString(String Criterion, Object value, UUID userID) {
        List<Message> searchedMessages = retrieveMessageByCriterion(Criterion, value, userID);
        StringBuilder searchedMsgStr = new StringBuilder();
        for(Message msg : searchedMessages) {
            searchedMsgStr.append(msg.toString() + "\n");
        }
        if (searchedMsgStr.toString().isEmpty()) return "Search results: 0";
        return searchedMsgStr.toString();
    }

    private List<Message> retrieveMessageByCriterion(String criterion, Object value, UUID userID) {
        List<Message> matchedMessages = new ArrayList<>();
        List<Message> inbox = tempInbox.get(userID);
        try{
            for (Message msg : inbox) {
                Class<?> msgType = msg.getClass();
                Method desiredMethod = msgType.getMethod("get"+criterion);
                if(desiredMethod.invoke(msgType).equals(value)) {
                    matchedMessages.add(msg);
                }
            }
        } catch (ReflectiveOperationException e) {
            return new ArrayList<>();
        }

        return matchedMessages;
    }
    */
}
